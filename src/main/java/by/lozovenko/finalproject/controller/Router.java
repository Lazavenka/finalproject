package by.lozovenko.finalproject.controller;

import static by.lozovenko.finalproject.controller.PagePath.INDEX;

public class Router {
    public enum DispatchType {
        FORWARD, REDIRECT
    }
    private String page = INDEX;
    private DispatchType type = DispatchType.FORWARD;
    public Router(String page, DispatchType type){
        this.page = page;
        this.type = type;
    }
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public DispatchType getType() {
        return type;
    }

    public void setRedirect() {
        this.type = DispatchType.REDIRECT;
    }
}
