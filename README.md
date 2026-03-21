# terracotta

a gradle plugin for minecraft mod development. supports fabric, neoforge, ornithe, and babric out of the box, with an extension point for custom loaders

## status

early development.

- [ ] per-loader repository contributors (no speculative repo registration)
- [ ] mappings api and providers (e.g. mojmap, parchment, yarn)
- [ ] java version inference from minecraft version
- [ ] loom-based loader handlers (fabric, ornithe, babric)
- [ ] neoforge handler via moddevgradle
- [ ] common subproject support (vanilla minecraft classpath, no loader api)
- [ ] mod metadata extension (`modInfo`) on the root project
- [ ] comment-based source preprocessor for version-specific code blocks
  - [ ] version predicate evaluation (e.g. `≥`, `=`, `≤`) against the active minecraft version
  - [ ] editor-friendly syntax that keeps files valid java/kotlin regardless of active version
- [ ] modrinth publishing
- [ ] gradle plugin portal publication

## modules

- ` core ` shared abstractions layer: mod loader modeling, mappings api, repository contributors, publish targets, and extensions

