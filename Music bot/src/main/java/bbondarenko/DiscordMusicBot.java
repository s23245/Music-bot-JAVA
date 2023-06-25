package bbondarenko;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Widget;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DiscordMusicBot extends ListenerAdapter
{
    public static void main(String[] args)
    {
        String TOKEN_FILE_PATH = "data/token.txt";
        String BOT_TOKEN = readUsingFiles(TOKEN_FILE_PATH);

        JDA jda = JDABuilder.createDefault(BOT_TOKEN)
                        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                        .setActivity(Activity.playing("your feelings :("))
                        .setStatus(OnlineStatus.IDLE)
                        .addEventListeners(new DiscordMusicBot())
                                .build();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if(!event.isFromGuild()) return;
        if(!event.getMessage().getContentRaw().startsWith("!play")) return;
        if(event.getAuthor().isBot()) return;
        Guild guild = event.getGuild();

        VoiceChannel channel = guild.getVoiceChannelsByName("Комната отдыха",true).get(0);
        AudioManager  manager = guild.getAudioManager();

        manager.setSendingHandler(manager.getSendingHandler());
        manager.openAudioConnection(channel);

        System.out.println(guild.getMembers());

    }

    private static String readUsingFiles(String fileName)
    {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}