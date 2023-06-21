package com.example.ustdate.botConfiguration;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MyTelegramBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        MyTelegramBot bot = new MyTelegramBot();
        bot.runBot();
    }

    private void runBot() {
        try {
            // Set up the bot
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            System.out.println("Bot has started successfully.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("You said: " + messageText);

            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "KazhakoottamDatingbot";
    }
    
    @Override
    public String getBotToken() {
        return "6050477286:AAFMYsYDktUj8SEhq3L9Cx0sqV_Rq0D_JmY";
    }
}
