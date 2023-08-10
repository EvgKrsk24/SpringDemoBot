package Sobolev.SpringDemoBot.service;

import Sobolev.SpringDemoBot.config.BotConfig;

import lombok.extern.slf4j.Slf4j;

import Sobolev.SpringDemoBot.model.User;
import Sobolev.SpringDemoBot.model.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.List;

import java.security.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.lang.System;

import static java.lang.System.currentTimeMillis;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private UserRepository userRepository;
    final BotConfig config;
    static final String HELP_TEXT = "This bot is Demo bot, GL HF.\n\n" +

            "You can execute commands from the main menu on the left or by typing a command: \n\n" +
            "You can execute commands from the main menu on the left or by typing a command:\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /mydata to see data stored about yourself\n\n" +
            "Type /help to see this message again";

    public TelegramBot(BotConfig config) {
        this.config=config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "get a welcome message"));
        listofCommands.add(new BotCommand("/mydata", "get your data stored"));
        listofCommands.add(new BotCommand("/deletedata", "delete my data"));
        listofCommands.add(new BotCommand("/help", "info how to use this bot"));
        listofCommands.add(new BotCommand("/settings", "set your preferences"));
        try{
            this.execute(new SetMyCommands(listofCommands,new BotCommandScopeDefault(),null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }
    @Override
    public String getBotUsername() {
        return config.getBotname();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
           long chatId= update.getMessage().getChatId();

            switch (messageText) {
                case "/start":


                    registerUser(update.getMessage());

                        starCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;

                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;

                default: sendMessage(chatId, "Sorry, not available ");

            }

        }

    }

    private void registerUser(Message msg) {

        if(userRepository.findById(msg.getChatId()).isEmpty()) {

    var chatId = msg.getChatId();
    var chat = msg.getChat();

    User user = new User();

    user.setChatId(chatId);
    user.setFirstName(chat.getFirstName());
    user.setLastName(chat.getLastName());
    user.setUserName(chat.getUserName());
    user.setRegisteredData(new Date(System.currentTimeMillis()));

   // user.setRegisteredAt(new Timestamp(new Date(System.currentTimeMillis()),"X509"));
           // user.setRegisteredAt(null);
    //user.setRegisteredAt(new Timestamp(new Date(System.currentTimeMillis()),null));

    userRepository.save(user);
    log.info("user saved: " + user);

        }

    }

    private void starCommandReceived(long chatId, String name) {
        String answer= "Hi, "+name+", nice to meet you";
        sendMessage(chatId, answer);
        log.info("Replied to user " + name);

    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        }
        catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
            //throw new RuntimeException(e);
        }


    }


}
