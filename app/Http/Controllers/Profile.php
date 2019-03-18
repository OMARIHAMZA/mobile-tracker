<?php

namespace App\Http\Controllers;


use App\User;
use Illuminate\Http\Request;

class Profile extends Controller
{
    //

    public function __construct()
    {
        $this->middleware('auth:api');
    }


    public function addFriendship(Request $request){
        $this->validate($request,[
            'phone' => 'required'
        ]);
        $user = new User();
        $user->addFriendship($request->input('phone'));

        return response()->json([
            'success' => true,
            'message' => 'Request Sent To User Successfully'
        ]);
    }

    public function getFriendRequests(){
        return response()->json([
            'success' => true,
            'data' => auth()->user()->getFriendRequests()
        ]);
    }

    public function acceptFriendRequest(Request $request){

        $this->validate($request,[
            'request_id' => 'required|integer'
        ]);

        auth()->user()->acceptFriendRequest($request->input('request_id'));

        return response()->json([
            'success' => true,
            'message' => 'Friendship Added!'
        ]);
    }

    public function rejectFriendRequest(Request $request){

        $this->validate($request,[
            'request_id' => 'required|integer'
        ]);

        auth()->user()->acceptFriendRequest($request->input('request_id'));

        return response()->json([
            'success' => true,
            'message' => 'Request Rejected Successfully!'
        ]);
    }

    public function getFriends(){
        return response()->json([
            'success' => true,
            'data' => auth()->user()->getFriends()
        ]);
    }

}
