//USUARIO AUTENTICADO

export class User {
  private _login: string;
  private _password: string;
  private _authHeader: string;
  private _authenticated: boolean;
  private _userName: string;
  private _userSurname: string;
  private _userEmail: string;
  private _userBirthday: Date;
  private _userPhoto: string;
  private _passwordConfirm: string;

  constructor() {
    const user: User = JSON.parse(<string>localStorage.getItem('currentUser'));
    if (user != null) {
      this._login = user._login;
      this._password = user._password;
      this._userName = user._userName;
      this._userEmail = user._userEmail;
      this._userSurname = user._userSurname;
      this._userBirthday = user._userBirthday;
      this._userPhoto = user._userPhoto;
      this._passwordConfirm = user._passwordConfirm;
      this._authenticated = user._authenticated;
      this._authHeader = user._authHeader;
    } else {
      this._authenticated = false;
    }
  }

  get authHeader(): string {
    return this._authHeader;
  }

  set authHeader(value: string) {
    this._authHeader = value;
  }

  get authenticated(): boolean {
    return this._authenticated;
  }

  set authenticated(value: boolean) {
    this._authenticated = value;
  }

  get email(): string {
    return this._userEmail;
  }

  set email(value: string) {
    this._userEmail = value;
  }

  get passwordConfirm(): string {
    return this._passwordConfirm;
  }

  set passwordConfirm(value: string) {
    this._passwordConfirm = value;
  }

  get birthday(): Date {
    return this._userBirthday;
  }

  set birthday(value: Date) {
    this._userBirthday = value;
  }

  get photo(): string {
    return this._userPhoto;
  }

  set photo(value: string) {
    this._userPhoto = value;
  }
  get surname(): string {
    return this._userSurname;
  }

  set surname(value: string) {
    this._userSurname = value;
  }

  get name(): string {
    return this._userName;
  }

  set name(value: string) {
    this._userName = value;
  }
  get login(): string {
    return this._login;
  }

  set login(value: string) {
    this._login = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  //ALMACENA AL USUARIO LOGGEADO ASOCIADO A LA CLAVE currentUser EN FORMATO JSON
  public save() {
    localStorage.setItem('currentUser', JSON.stringify(this));
  }

  //ELIMINA EL USUARIO LOGGEADO
  public clear() {
    localStorage.removeItem('currentUser');
  }
}
