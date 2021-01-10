<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use Laravel\Passport\Client;
use ValidateRequests;
use Carbon\Carbon;
use App\User;
use App\Conversation;
use App\Message;
use League\CommonMark\Converter;

class MessageController extends Controller
{
    public function newMessage(Request $request)
    {
        $conversation_id = $request->conversation_id;
        $conversation = Conversation::where('id',$conversation_id)->first();
        if($conversation==null)
        {
            return response()
            ->json([
                'status' => (string)'404',
                'cause' => (string)"the conversation doesn't exist",
            ]);
        }

        $message =  Message::create([
            'conversation_id' => $conversation_id,
            'status' => '0',
            'reply_id' => $request->reply_id,
        ]);

        return response()
            ->json([
                'status' => (string)'200',
                'message_id' => (string)$message->id,
            ]);
    }


    public function blockUser(Request $request)
    {
        $user_id = $request->user()->id;
        $conversation = Conversation::where('id',$request->conversation_id)->first();
        $conversation->blocked='1';
        $conversation->save();


        if($conversation->p1 == $request->user()->id)
        {
            $recipient = $conversation->p2;
        }
        else
        {
            $recipient = $conversation->p1;
        }


        $user2 = User::where('id',$recipient)->first();
        $user2->decrement('reputation',1);
        $user2->save();


        $recipients = [User::where('id',$recipient)->first()->fcm_token];

        fcm()->to($recipients)->priority('high')->timeToLive(0)
        ->data([
            'status' => '200',
            'code' => '4',
            'conversation_id' => $request->conversation_id,
        ])->send();

        return response()
        ->json([
            'status' => (string)'200',
        ]);
    }


    public function conversationSeenAcknowledgement(Request $request)
    {
        Message::where([['conversation_id',$request->conversation_id],['status','2']])->update(['status' => 2]);
        $conversation = Conversation::where('id',$request->conversation_id)->first();
        if($conversation->p1 == $request->user()->id)
        {
            $recipient = $conversation->p2;
        }
        else
        {
            $recipient = $conversation->p1;
        }
        $recipients = [User::where('id',$recipient)->first()->fcm_token];

        fcm()->to($recipients)->priority('high')->timeToLive(0)
        ->data([
            'status' => '200',
            'code' => '3',
            'conversation_id' => $request->conversation_id,
        ])->send();

        return response()
        ->json([
            'status' => (string)'200',
        ]);
    }


    public function messageReceivedAcknowledgement(Request $request)
    {
        $message = Message::where('id',$request->message_id)->first();
        $message->status=2;
        $message->save();
        $conversation = Conversation::where('id',$message->conversation_id)->first();
        if($conversation->p1 == $request->user()->id)
        {
            $recipient = $conversation->p2;
        }
        else
        {
            $recipient = $conversation->p1;
        }
        $recipients = [User::where('id',$recipient)->first()->fcm_token];

        fcm()->to($recipients)->priority('high')->timeToLive(0)
        ->data([
            'status' => '200',
            'code' => '2',
            'message_id' => $request->message_id,
        ])->send();

        return response()
        ->json([
            'status' => (string)'200',
        ]);
    }


    public function sendSelf(Request $request)
    {
        $conversation_id = $request->conversation_id;
        $conversation = Conversation::where('id',$conversation_id)->first();
        if($conversation==null)
        {
            return response()
            ->json([
                'status' => (string)'404',
                'cause' => (string)"the conversation doesn't exist",
            ]);
        }

        $message =  Message::create([
            'conversation_id' => $conversation_id,
            'status' => '3',
            'reply_id' => '0',
        ]);

        $recipients = [$request->user()->fcm_token];

        sleep($request->seconds);

        fcm()->to($recipients)->priority('high')->timeToLive(0)
        ->data([
            'status' => '200',
            'code' => '5',
            'message_id' => $message->id,
            'conversation_id' => $conversation->id,
            'text' => $request->text,
            'reply_id' =>'0',
        ])->send();


        return response()
        ->json([
            'status' => (string)'200',
        ]);

    }


    public function sendMessage(Request $request)
    {
        $encrypted_message = $request->message;
        $message = Message::where('id', $request->message_id)->update(['status' => 1]);
        $conversation = Conversation::where('id',$request->conversation_id)->first();


        if($conversation->p1 == $request->user()->id)
        {
            $recipient = $conversation->p2;
        }
        else
        {
            $recipient = $conversation->p1;
        }

        $user = User::where('id',$recipient)->first();
        $recipients = [$user->fcm_token];

        error_log('New Message is sent to: '.$user->nickname);

        fcm()->to($recipients)->priority('high')->timeToLive(0)
        ->data([
            'status' => '200',
            'code' => '1',
            'message_id' => $request->message_id,
            'conversation_id' => $conversation->id,
            'text' => $encrypted_message,
            'reply_id' => $request->reply_id,
        ])->send();

        return response()
        ->json([
            'status' => (string)'200',
        ]);
    }
}
