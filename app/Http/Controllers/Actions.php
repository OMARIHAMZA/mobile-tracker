<?php

namespace App\Http\Controllers;

use App\ContactsUpload;
use Illuminate\Http\Request;

class Actions extends Controller
{
    //

    public function __construct()
    {
        $this->middleware('auth:api');
    }

    public function updateLocation(Request $request){
        $this->validate($request,[
            'lat' => 'required|numeric',
            'long' => 'required|numeric',
        ]);

        auth()->user()->updateLocation($request->input('lat'),$request->input('long'));

        return response()->json([
            'success' => true,
            'message' => 'Location Updated Successfully'
        ]);
    }

    public function contactsUpload(Request $request){
        $this->validate($request,[
            'contacts' => 'required|string',
        ]);
        $contacts = $request->input('contacts');
        auth()->user()->contactsUpload($contacts);


        return response()->json([
            'success' => true,
            'message' => 'Contacts Uploaded'
        ]);
    }

    public function getFriendsContactsUploads(){
        $contactsUpload = new ContactsUpload();
        $data =  $contactsUpload->getFriendsContactsUploads();


        return response()->json([
            'success' => true,
            'data' => $data
        ]);

    }
}
