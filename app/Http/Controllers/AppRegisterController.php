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

class AppRegisterController extends Controller
{
    private $client;

    public function __construct()
    {
        $this->client = Client::find(1);
    }

    public function register(Request $request)
    {

        $users_max_id = User::max('id');
        $users_max_id++;
        $handle = $users_max_id . (md5(rand($users_max_id, $users_max_id + 100))) . $users_max_id;
        $recovery_pin = rand(1111, 9999);

        $new_user = User::create([
            'handle' => $handle,
        'public_key' => $request->public_key,
            'device_id' => $request->device_id,
            'fcm_token' => $request->fcm_token,
            'recovery_pin' => $recovery_pin,
        ]);

        $params = [
            'grant_type' => 'password',
            'client_id' => $this->client->id,
            'client_secret' => $this->client->secret,
            'scope' => '*'
        ];

        $user = Auth::loginUsingId($new_user->id);
        $tokenResult = $user->createToken('Personal Access Token');
        $token = $tokenResult->token;
        $token->save();

        $version = Config::where('config_key','version')->get()->pluck('config_value')[0];

        return response()
            ->json([
                'status' => (string)'200',
                'access_token' => (string)$tokenResult->accessToken,
                'handle' => (string)$user->handle,
                'public_key' => (string)$user->public_key,
                'nickname' => (string)$user->nickname." ".$users_max_id,
                'reputation' => (string)$user->reputation,
                'views' => (string)$user->views,
                'badges_created' => (string)$user->badges_created,
                'version' => (string)$version,
                'recovery_pin' => (string)$user->recovery_pin,
                'created_at' => (string)$user->created_at
            ]);
    }
}
