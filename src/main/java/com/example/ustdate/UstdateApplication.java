package com.example.ustdate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.ustdate.botConfiguration.MyTelegramBot;


@SpringBootApplication
public class UstdateApplication {

	public static void main(String[] args) {
		SpringApplication.run(UstdateApplication.class, args);
	}

	@Bean
    public MyTelegramBot myTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        MyTelegramBot bot = new MyTelegramBot();
        bot.clearWebhook(); // Clear the old webhook
        botsApi.registerBot(bot);
        return bot;
    }

}
