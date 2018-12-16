package xxxdemo.demo.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class PushPayload {
    //推送类型
    private String type;
    //推送对象
    private String mobile;
    //标题
    private String title;
    //内容
    private String content;
    //数量
    private Integer badge = 1;
    //铃声
    private String sound = "default";


    public PushPayload(String type, String mobile, String title, String content, Integer badge , String sound){
        this.type = type;
        this.mobile = mobile;
        this.title = title;
        this.content = content;
        this.badge = badge;
        this.sound = sound;
    }

    @Override
    public String toString() {
        return "PushPayload{" +
                "type=\"" + type + '\"' +
                ", mobile=\"" + mobile + '\"' +
                ", title=\"" + title + '\"' +
                ", content=\"" + content + '\"' +
                ", badge=" + badge +
                ", sound=\"" + sound + '\"' +
                '}';
    }

    public static class Builder{
        //推送类型
        private String type;
        //推送对象
        private String mobile;
        //标题
        private String title;
        //内容
        private String content;
        //数量
        private Integer badge = 1;
        //铃声
        private String sound = "default";

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setBadge(Integer badge) {
            this.badge = badge;
            return this;
        }

        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        public PushPayload bulid(){
            return new PushPayload(type,mobile,title,content,badge,sound);
        }


    }


    public static Builder getPushPayloadBuider(){
        return new Builder();
    }



}
