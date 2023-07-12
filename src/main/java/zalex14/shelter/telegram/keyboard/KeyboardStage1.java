package zalex14.shelter.telegram.keyboard;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendLocation;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import zalex14.shelter.service.shelter.ShelterService;
import zalex14.shelter.telegram.BotListener;

/**
 * The keyboard for stage 1:  Consult to a new visitor
 *
 * @see InlineKeyboardMarkup
 * @see InlineKeyboardButton
 * @see com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup
 * @see com.pengrad.telegrambot.TelegramBot
 * @see <a href="https://giters.com/pengrad/java-telegram-bot-api">InlineKeyboardMarkup</a>
 */
@Component
@Data
@RequiredArgsConstructor
public class KeyboardStage1 {
    private final Logger logger = LoggerFactory.getLogger(BotListener.class);
    private final TelegramBot bot;
    private final ShelterService service;
    private final Branch branch;

    /**
     * The first menu for stage 1:  Consult to a new visitor
     * 11 The shelter info
     * 12 The shelter schedule, address and driving map
     * 13 The security contacts for an auto ID
     * 14 General safety rules while staying at the shelter area
     * 15 Store of visitor's contacts for a feedback
     * 16 To call for a volunteer
     *
     * @param shelterId the shelter's id
     * @param update    The telegram update obj
     */
    public void menu(Long shelterId, Update update) {
        logger.info("Processing Stage1 : shelterId " + shelterId + ": {}", update);
        CallbackQuery callbackQuery = update.callbackQuery();
        Long chatId = update.callbackQuery().from().id();

        if (Item.ABOUT.s.equals(callbackQuery.data())) {
            bot.execute(new SendMessage(chatId, "Приют " + service.readById(shelterId).getTitle() + "\n"
                    + service.readById(shelterId).getInformation()));

        } else if (Item.SCHEDULE.s.equals(callbackQuery.data())) {
            locationMap(shelterId, chatId);

        } else if (Item.SECURITY.s.equals(callbackQuery.data())) {
            bot.execute(new SendMessage(chatId, Item.SECURITY.s1 + "\n☎️ " +
                    service.readById(shelterId).getSecurityContacts() +
                    "\nНе забудьте: Выдача пропусков прекращается за 1 час до закрытия"));

        } else if (Item.SAFETY.s.equals(callbackQuery.data())) {
            bot.execute(new SendMessage(chatId, Item.SAFETY.s1 + "\n" +
                    service.readById(shelterId).getTerritoryStaying()));

        } else if (Item.FEEDBACK.s.equals(callbackQuery.data())) {
            branch.feedback(shelterId, update);

        } else if (Item.VOLUNTEER.s.equals(callbackQuery.data())) {
            branch.callVolunteer(shelterId, update);
        }
    }

    @AllArgsConstructor
    public enum Item {
        ABOUT("m11", "Рассказ о приюте"),
        SCHEDULE("m12", "Расписание работы, адрес и схема проезда"),
        SECURITY("m13", "Телефон охраны для оформления пропуска на машину"),
        SAFETY("m14", "Рекомендации о технике безопасности на территории приюта"),
        FEEDBACK("m15", "Оставить контактные данные для связи"),
        VOLUNTEER("m16", "Позвать волонтера");
        private final String s, s1;
    }

    /**
     * Keyboard for menu 1
     *
     * @return menu1
     */
    public InlineKeyboardMarkup keyboard() {
        InlineKeyboardButton m11 = new InlineKeyboardButton(Item.ABOUT.s1).callbackData(Item.ABOUT.s);
        InlineKeyboardButton m12 = new InlineKeyboardButton(Item.SCHEDULE.s1).callbackData(Item.SCHEDULE.s);
        InlineKeyboardButton m13 = new InlineKeyboardButton(Item.SECURITY.s1).callbackData(Item.SECURITY.s);
        InlineKeyboardButton m14 = new InlineKeyboardButton(Item.SAFETY.s1).callbackData(Item.SAFETY.s);
        InlineKeyboardButton m15 = new InlineKeyboardButton(Item.FEEDBACK.s1).callbackData(Item.FEEDBACK.s);
        InlineKeyboardButton m16 = new InlineKeyboardButton(Item.VOLUNTEER.s1).callbackData(Item.VOLUNTEER.s);
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{m11},
                new InlineKeyboardButton[]{m12},
                new InlineKeyboardButton[]{m13},
                new InlineKeyboardButton[]{m14},
                new InlineKeyboardButton[]{m15},
                new InlineKeyboardButton[]{m16});
    }

    /**
     * The shelter's direction : address, phone, operation and location map
     *
     * @param chatId    The telegram chat id
     * @param shelterId The shelter id
     */
    public void locationMap(long shelterId, long chatId) {
        float latitude = service.readById(shelterId).getLatitude();
        float longitude = service.readById(shelterId).getLongitude();
        bot.execute(new SendMessage(chatId, "Расположение приюта " + service.readById(shelterId).getTitle() +
                "\n(Кликите по карте для увеличения и построения маршрута)"));
        bot.execute(new SendLocation(chatId, latitude, longitude));
        bot.execute(new SendMessage(chatId, "Адрес приюта: " + service.readById(shelterId).getAddress() +
                "\nТелефон " + service.readById(shelterId).getPhone() +
                "\nМы работаем " + service.readById(shelterId).getWorkingHours() +
                "\nКоординаты для навигатора N" + latitude + " E" + longitude));
    }
}
