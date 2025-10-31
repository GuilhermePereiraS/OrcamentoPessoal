export {};

declare global {
    interface Window {
        Dashboard: {
            showSuccess(msg: string): void;
            showError(msg: string): void;
        };
    }
}
