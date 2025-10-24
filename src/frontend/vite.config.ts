import { defineConfig } from 'vite'
import path from 'path'

export default defineConfig({
    base: '/',
    build: {
        manifest: false,
        outDir: path.resolve(__dirname, '../main/resources/static/_src'),
        emptyOutDir: true,

        rollupOptions: {
            input: {
                'ts/main': path.resolve(__dirname, 'src/ts/main.ts'),
                'src/ts/pages/porquinho/porquinho': path.resolve(__dirname, 'src/ts/pages/porquinho/porquinho.ts'),
            },

            // Isto remove os hashes dos nomes dos ficheiros.
            output: {
                // [name].js -> main.js, login.js, dashboard.js
                entryFileNames: `[name].js`,
                // Outros pedaços de código partilhado
                chunkFileNames: `[name].js`,
                // [name].css -> main.css, login.css, dashboard.css
                assetFileNames: `[name].[ext]`
            }
        },
    },
    server: {
        port: 5173,
        proxy: {
            '/api': 'http://localhost:8080',
        },
    },
    resolve: {
        alias: {
            '@ts': path.resolve(__dirname, 'src/ts'),
            '@scss': path.resolve(__dirname, 'src/scss'),
        },
    },
})