<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Conversation extends Model
{
    protected $fillable = [
       'id','p1','p2','p1_nick','p2_nick','blocked','encrypted_password','salt'
    ];
}
