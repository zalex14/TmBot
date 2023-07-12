package zalex14.shelter.telegram.keyboard;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import zalex14.shelter.entity.customer.Customer;
import zalex14.shelter.entity.report.Message;
import zalex14.shelter.service.customer.CustomerService;
import zalex14.shelter.service.report.MessageService;
import zalex14.shelter.service.shelter.ShelterService;
import zalex14.shelter.telegram.BotListener;

import java.time.LocalDateTime;

/**
 * The Bot keyboard branches and messages
 */
@Component
@Data
@RequiredArgsConstructor
public class Branch {
    private final Logger logger = LoggerFactory.getLogger(BotListener.class);
    private final TelegramBot bot;
    private final ShelterService service;
    private final CustomerService customerService;
    private final MessageService messageService;

    /**
     * The shelter's title
     *
     * @param shelterId The shelter id
     */
    public String title(long shelterId) {
        String branch;

        if (shelterId == 1) {
            branch = " для собак ";
        } else {
            branch = " для кошек ";
        }
        return service.readById(shelterId).getTitle() + branch;
    }

    /**
     * Call to a volunteer
     *
     * @param shelterId The shelter's id
     * @param update    The telegram update
     */
    public void callVolunteer(Long shelterId, Update update) {
        logger.info("Processing callVolunteer : shelterId " + shelterId + ": {}", update);

        User user = update.callbackQuery().from();
        Message message = new Message();
        message.setTelegramId(user.id());
        message.setComment(user.username() + " ( " + user.firstName() + " " + user.lastName() + ") позвал волонтера");
        message.setMessageTime(LocalDateTime.now());
        message.setIsReply(false);
        messageService.create(message);

        bot.execute(new SendMessage(user.id(), user.firstName() + ", c Вами свяжется волонтер Приюта "
                + title(shelterId)));
    }

    /**
     * Feedback message
     *
     * @param shelterId The shelter's id
     * @param update    The telegram update
     */
    public void feedback(Long shelterId, Update update) {
        logger.info("Processing feedback : shelterId " + shelterId + ": {}", update);
        Long chatId = update.callbackQuery().from().id();
        String person = update.callbackQuery().from().firstName() + " " + update.callbackQuery().from().lastName();

        Message message = new Message();
        message.setTelegramId(chatId);
        message.setMessageTime(LocalDateTime.now());
        message.setIsReply(false);
        message.setComment(update.callbackQuery().from().username() + " (" + person + ") оставил контакты для связи");
        messageService.create(message);

        bot.execute(new SendMessage(chatId, update.callbackQuery().from().firstName()
                + ", Ваши контакты отправлены в Приют " + title(shelterId)));
    }

    /**
     * Send contact vCard via send your phone at Telegram
     *
     * @param update The telegram update
     * from=User{id=1632254807, first_name='Андрей', last_name='905', username='PAA905', date=1686391950,
     *   chat=Chat{id=1632254807, type=Private, first_name='Андрей', last_name='905', username='PAA905',
     *      title='null', photo=null,
     *   contact=Contact{phone_number='79052224018', first_name='Андрей', last_name='905', user_id=1632254807}
     */
    public void sendContact(Update update) {
        Long chatId = update.message().contact().userId();
        String fullName = update.message().contact().firstName() + " " + update.message().contact().lastName();
        String phone = update.message().contact().phoneNumber();
        String telegramUserName = update.message().from().username();

        String card = " ф.и.о.: " + fullName + " user: @" + telegramUserName + " (chatId: " + chatId + ") Тел:" + phone;
        logger.info("Processing sendContacts {}", card);

        Message message = new Message();
        message.setTelegramId(chatId);
        message.setMessageTime(LocalDateTime.now());
        message.setComment(card);
        message.setIsReply(false);
        messageService.create(message);

        // add customer's phone number to customer db if isn't exist
        if (customerService.isCustomerExist(chatId)) {
            Customer customer = customerService.readById(chatId);
            customerService.update(chatId, customer);
        }

        SendMessage sendMessage = new SendMessage(chatId, " Контакты отправлены");
        if (!bot.execute(sendMessage).isOk()) {
            logger.error("Error: {}", bot.execute(sendMessage).description());
        }
    }

//    /**
//     * The executing method title
//     *
//     * @return obj method name
//     */
//    public String getExecutingMethodName() {
//        return new Object() {
//        }.getClass().getEnclosingMethod().getName();
//    }

    /**
     * the first bot greeting
     */
    public static final String GREETING = """
            Вас приветствует телеграм-бот приютов домашних питомцев, которые ждут своих новых хозяев.
            Сперва выберите приют и далее следуйте указаниям бота
            """;
    /**
     * the report rules
     */
    public static final String WELCOME = """
            Уважаемый усыновитель, после того как Вы забрали животное из приюта, Вы обязаны в течение месяца присылать
            информацию о том, как животное чувствует себя на новом месте. Отчет нужно присылать каждый день, ограничений
            в сутках по времени сдачи отчета нет. Каждый день волонтеры отсматривают все присланные отчеты после 21:00.
                        
            В стандартный ежедневный отчет входит:
                        
            Рацион питомца
                        
            Общее самочувствие и привыкание к новому месту
                        
            Изменение в поведении: отказ от старых, приобретение новых привычек
                        
            Фото питомца
            """;

    /**
     * a report does not meet the requirements
     */
    public static final String SAMPLE = """
            Стандартная форма ежедневного отчета о содержании питомца:
            1) Дневной рацион
                        
            2) Общее самочувствие и привыкание к новому месту
                        
            3) Изменение в поведении: отказ от старых и приобретение новых привычек
                        
            4) Дневное фото питомца
                        
            Отчет надо отправлять ежеденевно до 21.00
            """;

    /**
     * a report does not meet the requirements
     */
    public static final String INCOMPLETE = """
            Уважаемый усыновитель, ваш отчет не соотвествует стандартным требованиям. Пожалуйста, направьте нам подробный
            отчет. Мы ответственны за наших питомцев. Иначе наши волонтеры будут должны проверить условия устройства и
            здоровье питомца.""";

    /**
     * report does not receive
     */
    public static final String REQUEST = """
            Уважаемый усыновитель, мы ждем ваш ежедневный отчет о питомце с информацией о рационе питания,
            общим самочувствии и првыкании к новому месту, изменениях в поведении и его фото.
            Отчет ждем ежедневно до 21.00.""";
}
