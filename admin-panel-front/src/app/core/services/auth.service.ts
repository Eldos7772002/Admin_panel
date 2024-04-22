import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, of, switchMap, throwError } from 'rxjs';

@Injectable({providedIn: 'root'})
export class AuthService
{
    private _authenticated: boolean = false;
    private _httpClient = inject(HttpClient);

    // -----------------------------------------------------------------------------------------------------
    // @ Accessors
    // -----------------------------------------------------------------------------------------------------

    /**
     * Setter & getter for access token
     */
    set accessToken(token: string) {
        if (typeof localStorage !== 'undefined') {
            localStorage.setItem('accessToken', token);
        }
    }
    
    get accessToken(): string
    {
        if (typeof localStorage !== 'undefined') {
            return localStorage.getItem('accessToken') ?? '';
        } else {
            return '';
        }
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Forgot password
     *
     * @param email
     */
    forgotPassword(email: string): Observable<any>
    {
        return this._httpClient.post('api/auth/forgot-password', email);
    }

    /**
     * Reset password
     *
     * @param password
     */
    resetPassword(password: string): Observable<any>
    {
        return this._httpClient.post('api/auth/reset-password', password);
    }

    /**
     * Sign in
     *
     * @param credentials
     */
    signIn(credentials: { email: string; password: string }): Observable<any> {
        return this._httpClient.post('/gateway/api/v1/auth/signin', credentials).pipe(
        switchMap((response: any) => {
            // Check if the response is successful
            if (response.statusCode === 200) {
            // Store the access token and set authentication flag
            this.accessToken = response.accessToken;
            this._authenticated = true;
            // Return a new observable with the response
            return of(response);
            } else {
            // Return an error observable for other status codes
            return of(response);
        }
        }),
        catchError(error => {
            // Handle HTTP errors here
            return throwError(error);
        })
        );
    }
  

    /**
     * Sign in using the access token
     */
    signInUsingToken(): Observable<any>
    {
        // Sign in using the token
        return this._httpClient.post('/gateway/api/v1/auth/refresh', {
            token: this.accessToken,
        }).pipe(
            catchError(() =>

                // Return false
                of(false),
            ),
            switchMap((response: any) =>
            {
                // Replace the access token with the new one if it's available on
                // the response object.
                //
                // This is an added optional step for better security. Once you sign
                // in using the token, you should generate a new one on the server
                // side and attach it to the response object. Then the following
                // piece of code can replace the token with the refreshed one.
                if ( response.accessToken )
                {
                    this.accessToken = response.accessToken;
                }

                // Set the authenticated flag to true
                this._authenticated = true;

                // Return true
                return of(true);
            }),
        );
    }

    /**
     * Sign out
     */
    signOut(): Observable<any>
    {
        // Remove the access token from the local storage
        localStorage.removeItem('accessToken');

        // Set the authenticated flag to false
        this._authenticated = false;

        // Return the observable
        return of(true);
    }

    /**
     * Sign up
     *
     * @param user
     */
    signUp(user: { name: string; email: string; password: string; company: string }): Observable<any>
    {
        return this._httpClient.post('/gateway/api/v1/auth/signup', user);
    }

    /**
     * Unlock session
     *
     * @param credentials
     */
    unlockSession(credentials: { email: string; password: string }): Observable<any>
    {
        return this._httpClient.post('/gateway/api/v1/auth/signin', credentials);
    }

    /**
     * Check the authentication status
     */
    check(): Observable<boolean>
    {
        // Check if the user is logged in
        if ( this.accessToken )
        {
            return of(true);
        }

        // Check the access token availability
        if ( !this.accessToken )
        {
            return of(false);
        }

        // If the access token exists, and it didn't expire, sign in using it
        // return this.signInUsingToken();
    }
}
