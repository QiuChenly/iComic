package com.qiuchenly.comicx.ProductModules.Bika;

public class ChatMessageObject extends ChatBaseObject {
    public String activation_date;
    public String at;
    public String audio;
    public String avatar;
    public String block_user_id;
    public String character;
    public String[] characters;
    public String email;
    public String[] event_colors;
    public String event_icon;
    public String gender;
    public String image;
    public int level;
    public String message;
    public String name;
    public String platform;
    public String reply;
    public String reply_name;
    public String title;
    public ChatroomToObject to;
    public int type;
    public String unique_id;
    public String user_id;
    public boolean verified;

    public ChatMessageObject(String userId, String unique_id, int level, String email, String avatar, String name, String title, String gender, String platform, String activationDate, String at, String replyName, String reply, String message, String image, String audio, String block_user_id, int type, boolean verified, String character, String[] characters, String event_icon, String[] event_colors) {
        this.user_id = userId;
        this.unique_id = unique_id;
        this.level = level;
        this.email = email;
        this.avatar = avatar;
        this.name = name;
        this.title = title;
        this.gender = gender;
        this.platform = platform;
        this.activation_date = activationDate;
        this.at = at;
        this.reply_name = replyName;
        this.reply = reply;
        this.message = message;
        this.image = image;
        this.audio = audio;
        this.block_user_id = block_user_id;
        this.type = type;
        this.verified = verified;
        this.character = character;
        this.characters = characters;
        this.event_icon = event_icon;
        this.event_colors = event_colors;
        this.to = null;
    }

    public ChatMessageObject(String userId, String unique_id, int level, String email, String avatar, String name, String title, String gender, String platform, String activationDate, String at, String replyName, String reply, String message, String image, String audio, String block_user_id, int type, boolean verified, String character, String[] characters, String event_icon, String[] event_colors, ChatroomToObject to) {
        this.user_id = userId;
        this.unique_id = unique_id;
        this.level = level;
        this.email = email;
        this.avatar = avatar;
        this.name = name;
        this.title = title;
        this.gender = gender;
        this.platform = platform;
        this.activation_date = activationDate;
        this.at = at;
        this.reply_name = replyName;
        this.reply = reply;
        this.message = message;
        this.image = image;
        this.audio = audio;
        this.block_user_id = block_user_id;
        this.type = type;
        this.verified = verified;
        this.character = character;
        this.characters = characters;
        this.event_icon = event_icon;
        this.event_colors = event_colors;
        this.to = to;
    }

    public String getUserId() {
        return this.user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public String getUniqueId() {
        return this.unique_id;
    }

    public void setUniqueId(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getActivationDate() {
        return this.activation_date;
    }

    public void setActivationDate(String activationDate) {
        this.activation_date = activationDate;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAt() {
        return this.at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getReplyName() {
        return this.reply_name;
    }

    public void setReplyName(String replyName) {
        this.reply_name = replyName;
    }

    public String getReply() {
        return this.reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAudio() {
        return this.audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReply_name() {
        return this.reply_name;
    }

    public void setReply_name(String reply_name) {
        this.reply_name = reply_name;
    }

    public String getBlockUserId() {
        return this.block_user_id;
    }

    public void setBlockUserId(String block_user_id) {
        this.block_user_id = block_user_id;
    }

    public String getCharacter() {
        return this.character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String[] getCharacters() {
        return this.characters;
    }

    public void setCharacters(String[] characters) {
        this.characters = characters;
    }

    public String getEventIcon() {
        return this.event_icon;
    }

    public void setEventIcon(String event_icon) {
        this.event_icon = event_icon;
    }

    public String[] getEventColors() {
        return this.event_colors;
    }

    public void setEventColors(String[] event_colors) {
        this.event_colors = event_colors;
    }

    public ChatroomToObject getTo() {
        return this.to;
    }

    public void setTo(ChatroomToObject to) {
        this.to = to;
    }

    public String toString() {
        return "ChatMessageObject{user_id='" + this.user_id + '\'' + ", unique_id='" + this.unique_id + '\'' + ", email='" + this.email + '\'' + ", avatar='" + this.avatar + '\'' + ", name='" + this.name + '\'' + ", title='" + this.title + '\'' + ", gender='" + this.gender + '\'' + ", platform='" + this.platform + '\'' + ", activation_date='" + this.activation_date + '\'' + ", at='" + this.at + '\'' + ", reply_name='" + this.reply_name + '\'' + ", reply='" + this.reply + '\'' + ", message='" + this.message + '\'' + ", image='" + this.image + '\'' + ", audio='" + this.audio + '\'' + ", block_user_id='" + this.block_user_id + '\'' + ", type='" + this.type + '\'' + ", level='" + this.level + '\'' + ", verified='" + this.verified + '\'' + ", character='" + this.character + '\'' + ", characters='" + this.characters + '\'' + ", event_icon='" + this.event_icon + '\'' + ", event_colors='" + this.event_colors + '\'' + ", to=" + this.to + '\'' + '}';
    }
}
