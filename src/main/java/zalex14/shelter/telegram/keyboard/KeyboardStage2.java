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
import zalex14.shelter.service.shelter.HandlerService;
import zalex14.shelter.service.shelter.ShelterService;
import zalex14.shelter.telegram.BotListener;

/**
 * The  keyboard for stage 2: Consult to a potential adopter
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
public class KeyboardStage2 {
    private final Logger logger = LoggerFactory.getLogger(BotListener.class);
    private final TelegramBot bot;
    private final ShelterService service;
    private final HandlerService serviceHandler;
    private final Branch branch;

    /**
     * The second menu for stage 2: Consult to a potential adopter
     * * 20 How to first meet of a pet and initial dating
     * * 21 Papers to adopt of a pet from the shelter
     * * 23 How to travel with a pet from the shelter
     * * 24 Arrangement of puppy at new place
     * * 25 Arrangement of adult pet at new place
     * * 26 Arrangement of sick pet at new place
     * * 27 Handler's advices
     * * 28 Recommended handlers
     * * 29 To call for a volunteer
     *
     * @param shelterId the shelter's id
     * @param update    The telegram update obj
     */
    public void menu(Long shelterId, Update update) {
        logger.info("Processing Stage2 : shelterId " + shelterId + ": {}", update);
        String callback = update.callbackQuery().data();
        Long chatId = update.callbackQuery().from().id();

        if (Item.DATING.s.equals(callback)) {
            bot.execute(new SendMessage(chatId, Item.DATING.s1 + "\n" +
                    service.readById(shelterId).getPetAcquaintance()));

        } else if (Item.DOCUMENTS.s.equals(callback)) {
            bot.execute(new SendMessage(chatId, Item.DOCUMENTS.s1 + "\n" +
                    service.readById(shelterId).getDocumentList()));

        } else if (Item.TRAVEL.s.equals(callback)) {
            bot.execute(new SendMessage(chatId, Item.TRAVEL.s1 + "\n" +
                    service.readById(shelterId).getTravelRecommendation()));

        } else if (Item.PUPPY.s.equals(callback)) {
            bot.execute(new SendMessage(chatId, Item.PUPPY.s1 + "\n" +
                    service.readById(shelterId).getChildArranging()));

        } else if (Item.ADULT.s.equals(callback)) {
            bot.execute(new SendMessage(chatId, Item.ADULT.s1 + "\n" +
                    service.readById(shelterId).getAdultArranging()));

        } else if (Item.SICK.s.equals(callback)) {
            bot.execute(new SendMessage(chatId, Item.SICK.s1 + "\n" +
                    service.readById(shelterId).getSickArrangement()));

        } else if (shelterId == 1 && Item.ADVICE.s.equals(callback)) {
            bot.execute(new SendMessage(chatId, Item.ADVICE.s1 + "\n" +
                    service.readById(shelterId).getHandlerAdvice()));

        } else if (shelterId == 1 && Item.HANDLERS.s.equals(callback)) {
            bot.execute(new SendMessage(chatId, Item.HANDLERS.s1 + "\n" +
                    serviceHandler.readAll().toString()));

        } else if (Item.FEEDBACK.s.equals(callback)) {
            branch.feedback(shelterId, update);

        } else if (Item.VOLUNTEER.s.equals(callback)) {
            branch.callVolunteer(shelterId, update);
        }
    }

    @AllArgsConstructor
    public enum Item {
        DATING("m20", "Правила знакомства с животным до того, как его забрать"),
        DOCUMENTS("m21", "Список документов, чтобы взять питомца"),
        TRAVEL("m22", "Список рекомендаций по транспортировке"),
        PUPPY("m23", "Список рекомендаций по обустройству щенка/котенка"),
        ADULT("m24", "Список рекомендаций по обустройству взрослого питомца"),
        SICK("m25", "Список рекомендаций по обустройству питомца с ограниченными возможностями"),
        ADVICE("m26", "Советы кинолога по первичному общению"),
        HANDLERS("m27", "Рекомендации по проверенным кинологам"),
        FEEDBACK("m28", "Принять и записать контактные данные"),
        VOLUNTEER("m29", "Позвать волонтера");
        private final String s, s1;
    }

    /**
     * Keyboard for menu 2
     *
     * @return menu2
     */
    public InlineKeyboardMarkup keyboard(Long shelterId) {
        InlineKeyboardButton m20 = new InlineKeyboardButton(Item.DATING.s1).callbackData(Item.DATING.s);
        InlineKeyboardButton m21 = new InlineKeyboardButton(Item.DOCUMENTS.s1).callbackData(Item.DOCUMENTS.s);
        InlineKeyboardButton m22 = new InlineKeyboardButton(Item.TRAVEL.s1).callbackData(Item.TRAVEL.s);
        InlineKeyboardButton m23 = new InlineKeyboardButton(Item.PUPPY.s1).callbackData(Item.PUPPY.s);
        InlineKeyboardButton m24 = new InlineKeyboardButton(Item.ADULT.s1).callbackData(Item.ADULT.s);
        InlineKeyboardButton m25 = new InlineKeyboardButton(Item.SICK.s1).callbackData(Item.SICK.s);
        InlineKeyboardButton m26 = new InlineKeyboardButton(Item.ADVICE.s1).callbackData(Item.ADVICE.s);
        InlineKeyboardButton m27 = new InlineKeyboardButton(Item.HANDLERS.s1).callbackData(Item.HANDLERS.s);
        InlineKeyboardButton m28 = new InlineKeyboardButton(Item.FEEDBACK.s1).callbackData(Item.FEEDBACK.s);
        InlineKeyboardButton m29 = new InlineKeyboardButton(Item.VOLUNTEER.s1).callbackData(Item.VOLUNTEER.s);
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{m20},
                new InlineKeyboardButton[]{m21},
                new InlineKeyboardButton[]{m22},
                new InlineKeyboardButton[]{m23},
                new InlineKeyboardButton[]{m24},
                new InlineKeyboardButton[]{m25},
                new InlineKeyboardButton[]{m28},
                new InlineKeyboardButton[]{m29}
        );
        if (shelterId == 1) {
            keyboard.addRow(m26);
            keyboard.addRow(m27);
        }
        return keyboard;
    }
}
