package retrofit2.converter.gson.dto;

/**
 * Created by 江俊超 on 2017/1/19 0019.
 * <p>Gihub https://github.com/aohanyao</p>
 */

public class BaseResponseDto {

    /**
     * message : {"info":"{\"info\":\"密码错误！\",\"code\":\"1002\"}","code":"999999"}
     */

    private MessageBean message;

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * info : {"info":"密码错误！","code":"1002"}
         * code : 999999
         */

        private String info;
        private String code;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
