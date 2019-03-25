package jcifs.netbios;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.UnknownHostException;

public class UncheckedUnknownHostException extends UncheckedIOException
{
    public UncheckedUnknownHostException(IOException cause) {
        super(cause);
    }

    public static UncheckedUnknownHostException withChecked(String host) {
        return new UncheckedUnknownHostException(new UnknownHostException(host));
    }

    public static UncheckedUnknownHostException withChecked() {
        return new UncheckedUnknownHostException(new UnknownHostException());
    }
}
