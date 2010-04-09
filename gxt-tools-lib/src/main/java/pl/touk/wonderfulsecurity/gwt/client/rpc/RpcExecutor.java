/*
 * Copyright (c) 2008 TouK.pl
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.touk.wonderfulsecurity.gwt.client.rpc;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.core.client.GWT;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;
import pl.touk.wonderfulsecurity.gwt.client.ui.ServerCommunicationInfo;

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
public class RpcExecutor {

    private static ServerCommunicationQueue messageQueue = new ServerCommunicationQueue();
    private static final String message = "Proszę czekać...";
    private static IRpcErrorHandler errorHandler;

    public static void execute(RequestBuilder rb) {
        final RequestCallback originalCallback = rb.getCallback();

        /**
         * Using default message
         */
        ServerCommunicationInfo.show(message);
        messageQueue.add(message);

        /**
         * Replacing callback, to be able to get login page location from HTTP header
         */
        rb.setCallback(new RequestCallback() {

            public void onResponseReceived(Request request, Response response) {
                String header = response.getHeader("loginPage");
                if (header != null && header.length() > 0) {
                    Redirect.redirect(header);
                }
                refreshUserMessage();
                originalCallback.onResponseReceived(request, response);
            }

            public void onError(Request request, Throwable exception) {
                refreshUserMessage();
                if (errorHandler != null) {
                    errorHandler.handleError(request, exception);
                }
                originalCallback.onError(request, exception);
            }
        });

        try {
            rb.send();
        } catch (RequestException ex) {
            if (errorHandler != null) {
                errorHandler.handleError(ex);
            }
        }

    }

    /**
     * Sets rpc exception handler for handling exceptions from failed request
     * sending and thrown during RPC call.
     * @param handler
     * @see RequestBuilder#send()
     * @see RequestCallback#onError(com.google.gwt.http.client.Request, java.lang.Throwable)
     */
    public static void setErrorHandler(IRpcErrorHandler handler) {
        errorHandler = handler;

    }

    static void refreshUserMessage() {

        String message = (String) messageQueue.poll();

        if (!messageQueue.isEmpty()) {
            ServerCommunicationInfo.show(message);
        } else {
            ServerCommunicationInfo.hide();
        }
    }
}

class ServerCommunicationQueue extends AbstractQueue {

    protected ArrayList content = new ArrayList();

    public Iterator iterator() {
        return content.iterator();
    }

    public int size() {
        return content.size();
    }

    public boolean offer(Object o) {
        content.add(0, o);
        return true;
    }

    public Object poll() {
        return content.remove(0);

    }

    public Object peek() {
        return content.get(0);
    }
}
