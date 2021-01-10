<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Badge extends Model
{
    protected $fillable = [
        'id','created_by','title','description','foreground_graphic_id','background_graphic_id','foreground_color','background_color','url','active'
    ];
}
