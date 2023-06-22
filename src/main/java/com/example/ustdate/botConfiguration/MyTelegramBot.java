package com.example.ustdate.botConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.ustdate.service.ConnectorService;

public class MyTelegramBot extends TelegramLongPollingBot {

	@Autowired
	ConnectorService connectorService;
	
    public static void main(String[] args) {
        MyTelegramBot bot = new MyTelegramBot();
        bot.runBot();
    }

    private void runBot() {
        try {
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
            String userName = update.getMessage().getFrom().getUserName();
            String chatId = update.getMessage().getChatId().toString();
        	if(userName==null){
			userName = update.getMessage().getFrom().getFirstName();
		}
            SendMessage message = connectorService.intermediate(userName,chatId,messageText);
            message.setChatId(chatId);
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
