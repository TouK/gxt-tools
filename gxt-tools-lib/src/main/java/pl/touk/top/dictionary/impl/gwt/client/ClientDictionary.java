/*
 * Copyright (c) 2008 TouK.pl
 *
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
package pl.touk.top.dictionary.impl.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.http.client.RequestBuilder;
import pl.touk.wonderfulsecurity.gwt.client.rpc.*;

import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.top.dictionary.impl.gwt.client.rpc.DictionaryServiceGwtAsync;
import pl.touk.top.dictionary.impl.gwt.client.rpc.DictionaryServiceGwt;

import java.util.Map;

/**
 * This class should be used on client side to query server side dictionary service.
 * <p/>
 * Configure instance of this utility class by calling initialize  static method. Remember that initialize method makes asynchronous call
 * to server to fetch eager dictionary entries so dictionary is not functionall and you cannot use it until onSuccess method (second argument to initialize)
 * is called. Bottom line is that you always have to initialize your application in onSuccess method of this callback. Only then  you can be sure
 * that security is initialized
 *
 * @author Lukasz Kucharski lkc@touk.pl
 */
public final class ClientDictionary {
// ------------------------------ FIELDS ------------------------------

    private static boolean initialized;
    private static DictionaryServiceGwtAsync dictionaryServiceRpc;

    private static Map<String, Map<String, DictionaryEntry>> categorizedEntries;

    private static ClientDictionary INSTANCE;

// -------------------------- STATIC METHODS --------------------------

    /**
     * Initialize client side dictionary components. You have to call this method first before you do anyting by its API.
     * Remember that dictionary is fully initialized only after on succes in callback parameter is called. So fully initialize your
     * application in onSuccess method of callback given as parameter to this method
     *
     * @param dictionaryServiceEndpointUrl url of dictionaryService endpoint on server side eg "secure/rpc/dictionaryService.do"
     * @param callback                     this callback onFailue will be called when sth. bad happens when initializing client side dictionary
     *                                     (  cannot fetch startup entries, or you specified wrong dictionaryServiceEndpointUrl. onSuccess method is called
     *                                     when dictionary is fully initialized and ready to use. Initialize your application in onSuccess method of this callback.
     */
    public static void initialize(String dictionaryServiceEndpointUrl, final AsyncCallback callback) {
        if (initialized) {
            throw new IllegalStateException("Initialized already, should not be called again");
        }


        dictionaryServiceRpc = (DictionaryServiceGwtAsync) GWT.create(DictionaryServiceGwt.class);
        ServiceDefTarget securityManagerEndpoint = (ServiceDefTarget) dictionaryServiceRpc;
        securityManagerEndpoint.setServiceEntryPoint(dictionaryServiceEndpointUrl);


        RequestBuilder rb = dictionaryServiceRpc.fetchAllEntryObjectsCategorized(false, new AsyncCallback<Map<String, Map<String, DictionaryEntry>>>() {
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            public void onSuccess(Map<String, Map<String, DictionaryEntry>> dictionary) {
                INSTANCE = new ClientDictionary();
                initialized = true;
                categorizedEntries = dictionary;
                callback.onSuccess(null);
            }
        });

        RpcExecutor.execute(rb);
    }


    /**
     * Reinitialize client side dictionary components. You have to call this method first before you do anyting by its API.
     * Remember that dictionary is fully initialized only after on succes in callback parameter is called. So fully initialize your
     * application in onSuccess method of callback given as parameter to this method
     *
     * @param dictionaryServiceEndpointUrl url of dictionaryService endpoint on server side eg "secure/rpc/dictionaryService.do"
     * @param callback                     this callback onFailue will be called when sth. bad happens when initializing client side dictionary
     *                                     (  cannot fetch startup entries, or you specified wrong dictionaryServiceEndpointUrl. onSuccess method is called
     *                                     when dictionary is fully initialized and ready to use. Initialize your application in onSuccess method of this callback.
     */
    public static void reloadDictionaries(final AsyncCallback callback) {
        if (!initialized) {
            throw new IllegalStateException("First initialize component");
        }

        RequestBuilder rb = dictionaryServiceRpc.fetchAllEntryObjectsCategorized(false, new AsyncCallback<Map<String, Map<String, DictionaryEntry>>>() {
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            public void onSuccess(Map<String, Map<String, DictionaryEntry>> dictionary) {
                categorizedEntries = dictionary;
                callback.onSuccess(null);
            }
        });

        RpcExecutor.execute(rb);
    }

    /**
     * Get security rpc service in case you need it
     */
    public static DictionaryServiceGwtAsync getAsyncDictionaryService() {
        checkInitialized();

        return dictionaryServiceRpc;
    }

    private static void checkInitialized() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Did you forget to call initialize method? Are you using this method before asynchronous initialize() method completed?");
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static Map<String, DictionaryEntry> fetchCategoryAsObjects(String categoryName) {
        checkInitialized();
        return categorizedEntries.get(categoryName);
    }


    public static String transtalteKeyToStringValue(String key) {

        // TODO: it would be nice to cache search results
        for (Map<String, DictionaryEntry> category : categorizedEntries.values()) {

            DictionaryEntry entry = category.get(key);

            if (entry != null) {
                return entry.getValue();
            }

        }

        return null;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private ClientDictionary() {
        // prevent instantiation
    }
}