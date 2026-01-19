package dev.muriplz.mixins;

import com.hypixel.hytale.protocol.FormattedMessage;
import com.hypixel.hytale.server.core.Message;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = Message.class, remap = false)
public class MessageMixin {

    private static final String COLOR_CODES = "0123456789abcdefABCDEF";
    private static final String[] COLORS = {
            "#000000", "#0000AA", "#00AA00", "#00AAAA", "#AA0000", "#AA00AA", "#FFAA00", "#AAAAAA",
            "#555555", "#5555FF", "#55FF55", "#55FFFF", "#FF5555", "#FF55FF", "#FFFF55", "#FFFFFF"
    };

    /**
     * @author muriplz
     * @reason Minecraft-style formatting codes
     */
    @Overwrite
    public static Message raw(String message) {
        if (!message.contains("&")) {
            return createRaw(message);
        }

        Message result = Message.empty();
        StringBuilder current = new StringBuilder();

        boolean bold = false, italic = false;
        String color = null;

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);

            if (c == '\\' && i + 1 < message.length() && message.charAt(i + 1) == '&') {
                current.append('&');
                i++;
                continue;
            }

            if (c == '&' && i + 1 < message.length()) {
                char code = message.charAt(i + 1);

                if (isValidCode(code)) {
                    if (current.length() > 0) {
                        result.insert(applyFormat(current.toString(), bold, italic, color));
                        current.setLength(0);
                    }

                    int colorIndex = COLOR_CODES.indexOf(Character.toLowerCase(code));
                    if (colorIndex != -1) {
                        color = COLORS[colorIndex];
                        bold = italic = false;
                    } else {
                        switch (Character.toLowerCase(code)) {
                            case 'l' -> bold = true;
                            case 'o' -> italic = true;
                            case 'r' -> {
                                bold = italic = false;
                                color = null;
                            }
                        }
                    }
                    i++;
                    continue;
                }
            }

            current.append(c);
        }

        if (current.length() > 0) {
            result.insert(applyFormat(current.toString(), bold, italic, color));
        }

        return result;
    }

    private static Message createRaw(String text) {
        FormattedMessage fm = new FormattedMessage();
        fm.rawText = text;
        return new Message(fm);
    }

    private static boolean isValidCode(char c) {
        c = Character.toLowerCase(c);
        return COLOR_CODES.indexOf(c) != -1 || c == 'l' || c == 'o' || c == 'n' || c == 'm' || c == 'k' || c == 'r';
    }

    private static Message applyFormat(String text, boolean bold, boolean italic, String color) {
        Message msg = createRaw(text);
        if (bold) msg.bold(true);
        if (italic) msg.italic(true);
        if (color != null) msg.color(color);
        return msg;
    }
}