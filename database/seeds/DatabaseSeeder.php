<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // $this->call(UsersTableSeeder::class);

        $user = new \App\User();
        $user->username = 'samsaydali';
        $user->password = bcrypt('secret');
        $user->phone = '+963951419123';
        $user->brand = 'Apple';
        $user->phone_model = 'iPhone 5s';

        $user->save();
    }
}
