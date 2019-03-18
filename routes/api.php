<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/
Route::group([

    'middleware' => 'api',
    'prefix' => 'auth'

], function ($router) {

    Route::post('login', 'AuthController@login');
    Route::post('register', 'AuthController@register');
    Route::post('logout', 'AuthController@logout');
    Route::post('refresh', 'AuthController@refresh');
    Route::post('me', 'AuthController@me');

});

Route::group([

    'middleware' => 'api',
    'prefix' => 'profile'

], function ($router) {

    Route::get('getFriendRequests', 'Profile@getFriendRequests');
    Route::post('addFriendship', 'Profile@addFriendship');
    Route::post('acceptFriendRequest', 'Profile@acceptFriendRequest');
    Route::post('rejectFriendRequest', 'Profile@rejectFriendRequest');
    Route::get('getFriends', 'Profile@getFriends');

});

Route::group([

    'middleware' => 'api',
    'prefix' => 'actions'

], function ($router) {

    Route::post('updateLocation', 'Actions@updateLocation');
    Route::post('contactsUpload', 'Actions@contactsUpload');
    Route::get('getFriendsContactsUploads', 'Actions@getFriendsContactsUploads');

});