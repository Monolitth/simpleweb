package WebManager.send;

public enum SendEnum {
    REDIRECT {
        {
            this.send = new RedirectSend();
        }
    },
    REGISTER {
        {
            this.send = new RegisterSend();
        }
    },
    LOGIN {
        {
            this.send = new LoginSend();
        }
    },
    LOGOUT {
        {
            this.send = new LogoutSend();
        }
    },
    CHANGE {
        {
            this.send = new ChangeSend();
        }
    };

    InterfaceSend send;

    public InterfaceSend getCurrentSend() {
        return send;
    }
}
