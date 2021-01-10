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
use App\Config;
use App\Conversation;

class AppUserAccountController extends Controller
{
    public function publicKey(Request $request)
    {
        $handle = $request->handle;
        if ($handle == $request->user()->handle) {
            return response()
                ->json([
                    'status' => (string)'403',
                    'cause' => (string)"you are opening your own handle",
                ]);
        }
        $found = User::where('handle', $handle)->first();
        if ($found == null) {
            return response()
                ->json([
                    'status' => (string)'404',
                    'cause' => (string)"nothing found against this handle",
                ]);
        }

        return response()
        ->json([
            'status' => (string)'200',
            'public_key' => (string)$found->public_key,
        ]);
    }

    public function find(Request $request)
    {
        $handle = $request->handle;
        if ($handle == $request->user()->handle) {
            return response()
                ->json([
                    'status' => (string)'403',
                    'cause' => (string)"you are opening your own handle",
                ]);
        }

        $found = User::where('handle', $handle)->first();


        if ($found == null) {
            return response()
                ->json([
                    'status' => (string)'404',
                    'cause' => (string)"nothing found against this handle",
                ]);
        }

        $found->increment('views',1);
        $found->save();

        $conversation = Conversation::where([
            ['p1', $request->user()->id],
            ['p2', $found->id],
        ])->first();

        if ($conversation != null) {
            return response()
                ->json([
                    'status' => (string)'409',
                    'cause' => (string)"conversation already exist",
                    'conversation_id' => (string)$conversation->id,
                ]);
        }

        $conversation = Conversation::create([
            'p1' => $request->user()->id,
            'p2' => $found->id,
            'public_key' => $request->public_key,
            'encrypted_password' => $request->encrypted_password,
            'salt' => $request->salt,
        ]);



        return response()
            ->json([
                'status' => (string)'200',
                'conversation_id' => (string)$conversation->id,
            ]);
    }

    public function getConversationPassword(Request $request)
    {
        $conversation = Conversation::where('id',$request->conversation_id)->first();
        if ($conversation != null) {
            return response()
                ->json([
                    'status' => (string)'200',
                    'encrypted_password' => (string)$conversation->encrypted_password,
                    'salt' => (string)$conversation->salt,
                ]);
        }
    }

    public function getStats(Request $request)
    {
        $user = $request->user();
        return response()
                ->json([
                    'status' => (string)'200',
                    'reputation' => (string)$user->reputation,
                    'views' => (string)$user->views,
                ]);
    }
}
