import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
public class Bot extends TelegramLongPollingBot{

    private final Weather weather = new Weather();

    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        }catch (TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getBotUsername() {
        return "Weathernaut_bot";
    }

    @Override
    public String getBotToken() {
        return "6688115006:AAGszUbIR7Xz0PUQY6nLwyC3-kG1LU2VaAM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" ->
                        sendMessage(
                                chatId,
                                "Привет! Я помогу узнать погоду, просто напиши название города."
                        );
                case "/help" ->
                        sendMessage(
                                chatId,
                                "Чтобы узнать погоду, отправь название города."
                        );
                default ->
                        sendMessage(
                                chatId,
                                weather.getWeather(messageText)
                        );
            }
        }
    }

    public void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messageText);
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
