package api.applicationApi;

public enum StatusCode {
    CODE_200(200,"OK"),
    CODE_201(201,"CREATED"),
    CODE_204(204,""),
    CODE_400(400,""),
    CODE_401(401,"UNAUTHORIZED"),
    CODE_404(404,"NO CONTENT"),
    CODE_422(422,"Unprocessable Entity");

    private final int code;
    private final String msg;

    StatusCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
