<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->id();
            $table->string('handle');
            $table->tinyInteger('type_id')->default(1);
            $table->integer('referrer_id')->default(0);

            $table->string('public_key');
            $table->string('device_id');
            $table->string('fcm_token');

            $table->string('nickname')->default("Incognito");
            $table->integer('reputation')->default(100);
            $table->integer('views')->default(0);
            $table->integer('badges_created')->default(0);

            $table->integer('package')->default(1);

            $table->tinyInteger('active')->default(1);

            $table->integer('recovery_pin');

            $table->rememberToken();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
}
