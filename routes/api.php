<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;


Route::post('register', 'AppRegisterController@register');



Route::group(['middleware' => 'auth:api'], function () {

    Route::post('find', 'AppUserAccountController@find');
    Route::post('pubkey', 'AppUserAccountController@publicKey');
    Route::post('compose', 'MessageController@newMessage');
    Route::post('send', 'MessageController@sendMessage');
    Route::post('cpass', 'AppUserAccountController@getConversationPassword');
    Route::post('stats', 'AppUserAccountController@getStats');
    Route::post('received', 'MessageController@messageReceivedAcknowledgement');
    Route::post('seen', 'MessageController@conversationSeenAcknowledgement');
    Route::post('block', 'MessageController@blockUser');
    Route::post('self', 'MessageController@sendSelf');
});


Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});
