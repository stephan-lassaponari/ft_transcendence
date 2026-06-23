import { ErrorHandler, Injectable } from '@angular/core';

@Injectable()
export class SilentErrorHandler implements ErrorHandler {
  handleError(_error: unknown): void {}
}
