import { Component, inject, signal, computed, HostListener, ElementRef } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { RouteStateService } from '../../../core/services/route-state.service';
import { UserService } from '../../../core/services/user.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  private routeState = inject(RouteStateService);
  private userService = inject(UserService);
  private authService = inject(AuthService);
  private elRef = inject(ElementRef);

  username = this.userService.username;
  avatarLetter = this.userService.avatarLetter;
  avatarUrl = computed(() => this.userService.currentUser()?.avatarUrl);
  
  failedUrls = signal<Set<string>>(new Set());
  isAvatarFailed = computed(() => {
    const url = this.avatarUrl();
    return url ? this.failedUrls().has(url) : false;
  });

  // Delegado ao serviço compartilhado para evitar duplicação de lógica de rota.
  isLobby = this.routeState.isLobby;

  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  dropdownOpen = signal(false);

  toggleDropdown(): void {
    this.dropdownOpen.update(v => !v);
  }

  onLogout(): void {
    this.dropdownOpen.set(false);
    this.authService.logout();
  }

  onAvatarError(event: Event): void {
    const url = this.avatarUrl();
    if (url) {
      this.failedUrls.update(set => {
        const next = new Set(set);
        next.add(url);
        return next;
      });
    }
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    if (!this.elRef.nativeElement.contains(event.target)) {
      this.dropdownOpen.set(false);
    }
  }
}
