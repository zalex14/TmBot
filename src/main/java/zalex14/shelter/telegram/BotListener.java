package zalex14.shelter.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zalex14.shelter.entity.customer.Customer;
import zalex14.shelter.exception.ValidationException;
import zalex14.shelter.service.customer.CustomerService;
import zalex14.shelter.telegram.keyboard.*;

import java.util.List;

/**
 * The Telegram bot update
 *
 * @see <a href="https://t.me/ShelterPetsBot">ShelterPetsBot</a>
 */
@Component
@RequiredArgsConstructor
public class BotListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(BotListener.class);
    private final KeyboardStage0 stage0;
    private final KeyboardStage1 stage1;
    private final KeyboardStage2 stage2;
    private final KeyboardStage3 stage3;
    private final CustomerService service;
    private final Branch branch;

    private Long shelterId;

    // Create the bot passing from the token received
    @Autowired
    private TelegramBot bot;

    @PostConstruct
    public void init() {
        bot.execute(new SetMyCommands(new BotCommand("/start", "Запустить бота")));
        bot.setUpdatesListener(this);
    }

    /**
     * Subscribe to telegram updates
     *
     * @param updates The list of telegram updates
     * @return The telegram updates
     */
    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing start1 {}", update);

                if (update.callbackQuery() == null) {

                    // check for an automatic bot
                    if (update.message().from().isBot()) {
                        bot.execute(new SendMessage(update.message().chat().id(), "Ботам не сюда ("));
                        throw new ValidationException(" Ботам не сюда ( ");
                    }

                    // start the bot session
                    if ("/start".equals(update.message().text())) {

                        // check for new customer: if not exist? save to new customer db
                        if (!service.isCustomerExist(update.message().chat().id())) {
                            createNewUser(update);
                        }

                        // sent welcome message to user via the bot
                        bot.execute(new SendMessage(update.message().chat().id(), "Дорогой " +
                                update.message().chat().firstName() + "!\n" + Branch.GREETING).replyMarkup(menuStart()));
                    } else {
                        // save contact vcard via send your phone at Telegram
                        branch.sendContact(update);
                    }
                } else {
                    if ("/dog".equals(update.callbackQuery().data())) {
                        shelterId = 1L;
                    } else if ("/cat".equals(update.callbackQuery().data())) {
                        shelterId = 2L;
                    }
                    stage0.menu(shelterId, update);
                    stage1.menu(shelterId, update);
                    stage2.menu(shelterId, update);
                    stage3.menu(shelterId, update);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Start menu to select of shelter
     *
     * @return MenuStart
     * @see InlineKeyboardMarkup
     * @see InlineKeyboardButton
     * @see <a href="https://giters.com/pengrad/java-telegram-bot-api">InlineKeyboardMarkup</a>
     */
    public InlineKeyboardMarkup menuStart() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Приют для собак").callbackData("/dog"),
                new InlineKeyboardButton("Приют для кошек").callbackData("/cat"));
    }

    /**
     * Create new user from the telegram
     *
     * @param update The telegram bot update
     */
    public void createNewUser(Update update) {
        Customer person = new Customer();
        person.setFullName(update.message().from().firstName() + " " + update.message().from().lastName());
        person.setTelegramUserName(update.message().from().username());
        person.setTelegramId(update.message().from().id());
        person.setComment(update.message().text());
        service.create(person);
    }
}
