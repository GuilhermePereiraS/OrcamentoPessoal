package br.com.porquinho.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("vite")
public class ViteAssetService {

    private final ResourceLoader resourceLoader;

    private Map<String, ManifestEntry> manifest = new HashMap<>();

    public ViteAssetService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() {

        Resource manifestResource = resourceLoader.getResource("classpath:static/_src/.vite/manifest.json");

        try (InputStream inputStream = manifestResource.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            this.manifest = mapper.readValue(inputStream, new TypeReference<Map<String, ManifestEntry>>() {});
        } catch (IOException e) {
            String path = "null";
            if (manifestResource != null) {
                try {
                    path = manifestResource.getURL().toString();
                } catch (IOException ex) {
                    path = manifestResource.getDescription();
                }
            }
            System.err.println("AVISO: Falha ao carregar o manifesto Vite. Caminho tentado: " + path);
            System.err.println("Exceção detalhada: " + e.getMessage());
        }
    }

    public String asset(String entryKey) {
        ManifestEntry entry = manifest.get(entryKey);
        if (entry == null) {
            throw new RuntimeException("Entrada Vite não encontrada no manifesto: " + entryKey);
        }
        return "/_src/" + entry.getFile();
    }

    public List<String> css(String entryKey) {
        ManifestEntry entry = manifest.get(entryKey);
        if (entry == null || entry.getCss() == null) {
            return List.of();
        }
        return entry.getCss()
                    .stream()
                    .map(cssFile -> "/_src/" + cssFile)
                    .toList();
    }

    // --- Classe interna para mapear o JSON ---
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ManifestEntry {
        private String file;
        private List<String> css;

        // Getters e Setters
        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public List<String> getCss() {
            return css;
        }

        public void setCss(List<String> css) {
            this.css = css;
        }
    }
}