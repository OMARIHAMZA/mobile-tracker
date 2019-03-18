<?php

namespace App;
use Illuminate\Support\Facades\DB;
use Illuminate\Database\Eloquent\Model;

class ContactsUpload extends Model
{
    //
    public function user(){
        return $this->belongsTo('App\User','user_id','id');
    }

    public function getFriendsContactsUploads(){
        return DB::table('friendships')->select([
            'users.username','users.phone','users.id',
            'contacts_uploads.contacts'
        ])
            ->where('friendships.user_id',auth()->user()->id)
            ->join('users','added_id','=','users.id')
            ->join('contacts_uploads','users.id','=','contacts_uploads.user_id')
            ->orderBy('contacts_uploads.id','DESC')
            ->limit(1)
            ->get();
    }
}
