package com.whaves.scmu;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Douglas on 21/04/2016.
 */
public class Utils {


    public static CookieStoreImpl createCookieStoreImpl(CookieStore cookieStore){

        List<Cookie> cookies = cookieStore.getCookies();
        List<CookiesImpl> listCookies = new ArrayList<CookiesImpl>();
        for (Cookie c : cookies) {
            listCookies.add(
                    new CookiesImpl(c.getName(),
                            c.getValue(),
                            c.getComment(),
                            c.getCommentURL(),
                            c.getDomain(),
                            c.getExpiryDate(),
                            c.getPath(),
                            c.getPorts(),
                            c.getVersion(),
                            c.isPersistent(),
                            c.isSecure(),
                            c.isExpired(new Date())));
        }

        return new CookieStoreImpl(listCookies);
    }


    public static BasicCookieStore createApacheCookieStore(List<CookiesImpl> cookiesList){

        BasicClientCookie c1 = new BasicClientCookie(cookiesList.get(0).getName(),
                cookiesList.get(0).getValue() );
        BasicClientCookie c2 = new BasicClientCookie(cookiesList.get(1).getName(),
                cookiesList.get(1).getValue() );
        BasicClientCookie c3 = new BasicClientCookie(cookiesList.get(2).getName(),
                cookiesList.get(2).getValue() );

        BasicCookieStore ck = new BasicCookieStore();
        ck.addCookie(c1);
        ck.addCookie(c1);
        ck.addCookie(c1);

        return ck;
    }
}
