package zalex14.shelter.telegram.keyboard;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
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
 * The keyboard for stage 0.1 : start
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
public class KeyboardStage0 {
    private final Logger logger = LoggerFactory.getLogger(BotListener.class);
    private final TelegramBot bot;
    private final ShelterService service;
    private final Branch branch;
    private final KeyboardStage1 stage1;
    private final KeyboardStage2 stage2;
    private final KeyboardStage3 stage3;

    /**
     * The start menu for stage 0:
     * 01 General information about the shelter
     * 02 How to adopt a pet from the shelter
     * 03 To send the daily report
     * 04 To call for a volunteer
     *
     * @param shelterId the shelter's id
     * @param update    The telegram update obj
     */
    public void menu(Long shelterId, Update update) {
        logger.info("Processing Stage0 shelterId " + shelterId + ": {}", update);

        if ("/dog".equals(update.callbackQuery().data()) || "/cat".equals(update.callbackQuery().data())) {
            bot.execute(new SendMessage(update.callbackQuery().from().id(),
                    "Приют " + branch.title(shelterId)).replyMarkup(keyboard0()));

        } else if (Item.INFO.s.equals(update.callbackQuery().data())) {
            bot.execute(new SendMessage(update.callbackQuery().from().id(),
                    Item.INFO.s1 + branch.title(shelterId)).replyMarkup(stage1.keyboard()));

        } else if (Item.ADOPT.s.equals(update.callbackQuery().data())) {
            bot.execute(new SendMessage(update.callbackQuery().from().id(),
                    Item.ADOPT.s1 + branch.title(shelterId)).replyMarkup(stage2.keyboard(shelterId)));

        } else if (Item.REPORT.s.equals(update.callbackQuery().data())) {
            bot.execute(new SendMessage(update.callbackQuery().from().id(),
                    Item.REPORT.s1).replyMarkup(stage3.keyboard()));

        } else if (Item.VOLUNTEER.s.equals(update.callbackQuery().data())) {
            branch.callVolunteer(shelterId, update);
        }
    }

    @AllArgsConstructor
    public enum Item {
        INFO("m01", "Информация о приюте "),
        ADOPT("m02", "Как взять питомца из приюта "),
        REPORT("m03", "Отправить отчет о питомце "),
        VOLUNTEER("m04", "Позвать волонтера ");
        private final String s, s1;
    }

    /**
     * Keyboard for menu stage 0
     *
     * @return Start of the menu 0
     */
    public InlineKeyboardMarkup keyboard0() {
        InlineKeyboardButton m01 = new InlineKeyboardButton(Item.INFO.s1).callbackData(Item.INFO.s);
        InlineKeyboardButton m02 = new InlineKeyboardButton(Item.ADOPT.s1).callbackData(Item.ADOPT.s);
        InlineKeyboardButton m03 = new InlineKeyboardButton(Item.REPORT.s1).callbackData(Item.REPORT.s);
        InlineKeyboardButton m04 = new InlineKeyboardButton(Item.VOLUNTEER.s1).callbackData(Item.VOLUNTEER.s);
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{m01},
                new InlineKeyboardButton[]{m02},
                new InlineKeyboardButton[]{m03},
                new InlineKeyboardButton[]{m04});
    }
}
