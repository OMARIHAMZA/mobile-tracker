<?php

namespace App\Policies;

use App\User;
use App\Phone;
use Illuminate\Auth\Access\HandlesAuthorization;

class PhonePolicy
{
    use HandlesAuthorization;

    /**
     * Determine whether the user can view the phone.
     *
     * @param  \App\User  $user
     * @param  \App\Phone  $phone
     * @return mixed
     */
    public function view(User $user, Phone $phone)
    {
        //
    }

    /**
     * Determine whether the user can create phones.
     *
     * @param  \App\User  $user
     * @return mixed
     */
    public function create(User $user)
    {
        //
    }

    /**
     * Determine whether the user can update the phone.
     *
     * @param  \App\User  $user
     * @param  \App\Phone  $phone
     * @return mixed
     */
    public function update(User $user, Phone $phone)
    {
        return $user->id === $phone->user_id;
    }

    /**
     * Determine whether the user can delete the phone.
     *
     * @param  \App\User  $user
     * @param  \App\Phone  $phone
     * @return mixed
     */
    public function delete(User $user, Phone $phone)
    {
        //
    }
}
