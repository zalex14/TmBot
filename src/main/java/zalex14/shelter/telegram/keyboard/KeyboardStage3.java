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
import zalex14.shelter.telegram.BotListener;

/**
 * The keyboard for the stage 3: Send a daily report
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
public class KeyboardStage3 {
    private final Logger logger = LoggerFactory.getLogger(BotListener.class);
    private final TelegramBot bot;
    private final Branch branch;

    /**
     * The third menu for stage 3: Send a daily report
     * 31 Send a daily report
     * 32 Send a daily report
     *
     * @param update The telegram update obj
     */
    public void menu(Long shelterId, Update update) {
        logger.info("Processing Stage3 : shelterId " + shelterId + ": {}", update);
        String callback = update.callbackQuery().data();
        Long chatId = update.callbackQuery().from().id();

        if (Menu3.PATTERN.s.equals(callback)) {
            bot.execute(new SendMessage(chatId, Branch.SAMPLE));
        } else if (Menu3.REPORT.s.equals(callback)) {
            sendReport(shelterId, update);
        } else if (Menu3.VOLUNTEER.s.equals(callback)) {
            branch.callVolunteer(shelterId, update);
        }
    }

    @AllArgsConstructor
    public enum Menu3 {
        PATTERN("m31", "Прислать форму ежедневного отчета"),
        REPORT("m32", "Ежедневный отчет о питомце"),
        VOLUNTEER("m33", "Позвать волонтера");
        private final String s, s1;
    }

    /**
     * Keyboard for menu 3
     *
     * @return menu3
     */
    public InlineKeyboardMarkup keyboard() {
        InlineKeyboardButton m31 = new InlineKeyboardButton(Menu3.PATTERN.s1).callbackData(Menu3.PATTERN.s);
        InlineKeyboardButton m32 = new InlineKeyboardButton(Menu3.REPORT.s1).callbackData(Menu3.REPORT.s);
        InlineKeyboardButton m33 = new InlineKeyboardButton(Menu3.VOLUNTEER.s1).callbackData(Menu3.VOLUNTEER.s);
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{m31},
                new InlineKeyboardButton[]{m32},
                new InlineKeyboardButton[]{m33});
    }

    /**
     * Send the daily report
     *
     * @param update The shelter's id
     */
    public void sendReport(Long shelterId, Update update) {
        Long chatId = update.callbackQuery().from().id();
        String callback = update.callbackQuery().data();

        logger.info("Processing Stage3 (" + shelterId + "/ " + chatId + " ): update {}", callback);
        bot.execute(new SendMessage(chatId, update.callbackQuery().from().firstName()
                + ", Ваш отчет отправлен в Приют "));
    }
}
