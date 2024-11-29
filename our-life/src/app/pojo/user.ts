export interface User {
    username?: string;
    fullname?:string;
    profilePicture?:string;
    authToken?:string;
    refreshToken?:string;
    authTokenRoles?:[];
}
