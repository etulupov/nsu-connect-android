package ru.tulupov.nsuconnect.request;

import android.net.Uri;

public interface Config {
    Uri AJAX_ENDPOINT = Uri.parse("http://inctalk.net/ajax.php");
    Uri MSG_ENDPOINT = Uri.parse("http://msg.inctalk.net");
    Uri PROVIDERS_ENDPOINT = Uri.parse("http://inctalk.net/ajax.php?action=get_providers");
    int TIMEOUT_QUERY = 300000;
    int TIMEOUT_POOLING = 630000;
    int DEFAULT_MAX_RETRIES = 1;
}
