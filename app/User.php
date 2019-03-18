<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Tymon\JWTAuth\Contracts\JWTSubject;
use Exception;
use Illuminate\Support\Facades\DB;
use DateTime;
use App\ContactsUpload as ContactsUpload;

class User extends Authenticatable implements JWTSubject
{
    use Notifiable;

    public function __construct(array $attributes = [])
    {
        parent::__construct($attributes);
    }

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'username', 'phone', 'password','brand','phone_model','lat','long','location_last_updated'
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    /**
     * Get the identifier that will be stored in the subject claim of the JWT.
     *
     * @return mixed
     */
    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    /**
     * Return a key value array, containing any custom claims to be added to the JWT.
     *
     * @return array
     */
    public function getJWTCustomClaims()
    {
        return [];
    }

    public function contactsUploads(){
        return $this->hasMany('App\ContactsUpload');
    }

    /**
     * @param $lat
     * @param $long
     */
    public function updateLocation($lat ,$long){
        $this->lat = $lat;
        $this->long = $long;
        $location_last_updated = new DateTime();
        $this->location_last_updated = $location_last_updated->format('Y-m-d H:i:s');

        $this->save();
    }

    /**
     * @param $phone
     * @return User
     * @throws Exception
     */
    protected function searchByPhone($phone){
        $user = $this->where('phone',$phone);

        if(!$user->exists()){
            throw new Exception('User Not Found!');
        }

        return $user->first();
    }

    /**
     * @param $phone
     * @throws Exception
     */
    public function addFriendship($phone){
        $added_user = $this->searchByPhone($phone);
        $added_id = $added_user->id;
        DB::table('add_requests')
        ->insert([
            'user_id' => auth()->user()->id,
            'added_id' => $added_id
        ]);
        return ;
    }

    /**
     * @return array
     */
    public function getFriendRequests(){
        return DB::table('add_requests')->select([
            'add_requests.id as request_id',
            'users.username','users.phone','users.id as user_id','users.brand','users.phone_model'
            ])
            ->where('added_id',$this->id)
            ->join('users','user_id','=','users.id')
            ->get();
    }

    /**
     * @param $request_id
     * @throws Exception
     */
    public function acceptFriendRequest($request_id){
        $request = DB::table('add_requests')->where('id',$request_id);
        if(!$request->exists()){
            throw new Exception('Request Not Found!');
        }
        $request = $request->first();

        if($request->added_id != $this->id){
            throw new Exception('Action Unauthorized!');
        }

        DB::table('friendships')
            ->insert([
                'user_id' => $request->user_id,
                'added_id' => $request->added_id
            ]);
        DB::table('friendships')
            ->insert([
                'user_id' => $request->added_id,
                'added_id' => $request->user_id
        ]);

        DB::table('add_requests')->where('id', '=', $request->id)->delete();

        return;

    }

    /**
     * @param $request_id
     * @throws Exception
     */
    public function rejectFriendRequest($request_id){
        $request = DB::table('add_requests')->where('id',$request_id);
        if(!$request->exists()){
            throw new Exception('Request Not Found!');
        }
        $request = $request->first();

        if($request->added_id != $this->id){
            throw new Exception('Action Unauthorized!');
        }

        DB::table('add_requests')->where('id', '=', $request->id)->delete();

        return;

    }

    public function getFriends(){
        return DB::table('friendships')->select([
            'friendships.id as friendship_id',
            'users.username','users.phone','users.id as user_id','users.brand',
            'users.phone_model','users.long','users.lat','users.location_last_updated'
            ])
            ->where('user_id',$this->id)
            ->join('users','added_id','=','users.id')
            ->get();
    }

    public function contactsUpload($contacts){
        $contactsUpload = new ContactsUpload();
        $contactsUpload->contacts = $contacts;
        $this->contactsUploads()->save($contactsUpload);
    }

}
