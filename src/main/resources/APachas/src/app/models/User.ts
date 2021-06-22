//USUARIO AUTENTICADO

export class User {
  private _login: string;
  private _password: string;
  private _authHeader: string;
  private _authenticated: boolean;

  constructor() {
    const user: User = JSON.parse(<string>localStorage.getItem('currentUser'));
    if (user != null) {
      this._login = user._login;
      this._password = user._password;
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
