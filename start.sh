#!/bin/bash

# ═══════════════════════════════════════════════════════
#  Cometa Backend — Script de inicio
#  Ejecuta: chmod +x start.sh && ./start.sh
# ═══════════════════════════════════════════════════════

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
BOLD='\033[1m'
NC='\033[0m'

BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOGS_DIR="$BASE_DIR/logs"
mkdir -p "$LOGS_DIR"

PIDS=()

# ── Cleanup ────────────────────────────────────────────
cleanup() {
    echo -e "\n${YELLOW}Deteniendo todos los servicios...${NC}"
    for pid in "${PIDS[@]}"; do
        if kill -0 "$pid" 2>/dev/null; then
            pkill -P "$pid" 2>/dev/null
            kill "$pid" 2>/dev/null
        fi
    done
    wait 2>/dev/null
    echo -e "${GREEN}Servicios detenidos. Hasta luego.${NC}"
    exit 0
}
trap cleanup SIGINT SIGTERM

# ── Iniciar servicio en segundo plano ─────────────────
start_service() {
    local name="$1"
    local dir="$2"
    (cd "$dir" && bash mvnw spring-boot:run 2>&1 > "$LOGS_DIR/${name}.log") &
    local pid=$!
    PIDS+=("$pid")
    echo "$pid"
}

# ── Esperar a que un servicio responda (HTTP) ─────────
wait_for() {
    local name="$1"
    local url="$2"
    local fatal="${3:-true}"
    local timeout=120
    local elapsed=0

    printf "  %-30s" "Esperando $name..."
    # -s silencioso, --max-time 3 para no bloquear, sin -f para aceptar cualquier HTTP status
    while ! curl -s --max-time 3 -o /dev/null "$url" 2>/dev/null; do
        sleep 2
        elapsed=$((elapsed + 2))
        printf "."
        if [ $elapsed -ge $timeout ]; then
            if [ "$fatal" = "true" ]; then
                echo -e " ${RED}TIMEOUT${NC}"
                echo -e "${RED}  Error: $name no arrancó. Revisa: logs/${name}.log${NC}"
                cleanup
            else
                echo -e " ${YELLOW}SKIP${NC} (opcional)"
                return 1
            fi
        fi
    done
    echo -e " ${GREEN}OK${NC}"
    return 0
}

# ── Esperar a que un puerto TCP esté abierto ──────────
wait_for_port() {
    local name="$1"
    local port="$2"
    local fatal="${3:-true}"
    local timeout=120
    local elapsed=0

    printf "  %-30s" "Esperando $name..."
    while ! (echo > /dev/tcp/localhost/$port) 2>/dev/null; do
        sleep 2
        elapsed=$((elapsed + 2))
        printf "."
        if [ $elapsed -ge $timeout ]; then
            if [ "$fatal" = "true" ]; then
                echo -e " ${RED}TIMEOUT${NC}"
                echo -e "${RED}  Error: $name no arrancó. Revisa: logs/${name}.log${NC}"
                cleanup
            else
                echo -e " ${YELLOW}SKIP${NC} (opcional)"
                return 1
            fi
        fi
    done
    echo -e " ${GREEN}OK${NC}"
    return 0
}

# ── Pre-check: MySQL ──────────────────────────────────
check_mysql() {
    if ! (echo > /dev/tcp/localhost/3306) 2>/dev/null; then
        echo -e "${RED}ERROR: MySQL no está corriendo en localhost:3306${NC}"
        echo -e "Inicia MySQL primero y vuelve a ejecutar el script."
        exit 1
    fi
    echo -e "  MySQL en :3306                 ${GREEN}OK${NC}"
}

# ══════════════════════════════════════════════════════
echo -e "${BOLD}${BLUE}"
echo "╔════════════════════════════════════════╗"
echo "║      Cometa Backend — Iniciando        ║"
echo "╚════════════════════════════════════════╝"
echo -e "${NC}"

# 0. Pre-checks
echo -e "${BOLD}[0/5] Pre-checks${NC}"
check_mysql

# 1. Config Service
echo -e "\n${BOLD}[1/5] Config Service (puerto 8888)${NC}"
start_service "config" "$BASE_DIR/config-service"
wait_for_port "config" 8888

# 2. Registry Service (Eureka)
echo -e "\n${BOLD}[2/5] Registry Service — Eureka (puerto 8761)${NC}"
start_service "registry" "$BASE_DIR/registry-service"
wait_for_port "registry" 8761

# 3. Gateway + Admin (en paralelo)
echo -e "\n${BOLD}[3/5] Gateway + Admin Service${NC}"
start_service "gateway" "$BASE_DIR/gateway-service"
start_service "admin"   "$BASE_DIR/admin-service"
wait_for_port "gateway" 8080
wait_for_port "admin"   9090  "false"

# 4. Auth + Product + Cart + Invoice (en paralelo)
echo -e "\n${BOLD}[4/5] Auth · Product · Cart · Invoice${NC}"
start_service "auth"    "$BASE_DIR/auth-service/auth"
start_service "product" "$BASE_DIR/product"
start_service "cart"    "$BASE_DIR/cart-service"
start_service "invoice" "$BASE_DIR/invoice-service"

wait_for_port "auth"    8084
wait_for_port "product" 8082
wait_for_port "cart"    8086
wait_for_port "invoice" 8085

# 5. Listo
echo -e "\n${BOLD}[5/5] ¡Todo listo!${NC}"
echo -e "${GREEN}"
echo "╔══════════════════════════╦══════════════════════════════════════════╗"
echo "║ Servicio                 ║ URL                                      ║"
echo "╠══════════════════════════╬══════════════════════════════════════════╣"
echo "║ Eureka Dashboard         ║ http://localhost:8761                     ║"
echo "║ Spring Boot Admin        ║ http://localhost:9090                     ║"
echo "╠══════════════════════════╬══════════════════════════════════════════╣"
echo "║ Auth — login             ║ POST http://localhost:8080/auth/login     ║"
echo "║ Product — Swagger        ║ http://localhost:8082/swagger-ui.html     ║"
echo "╠══════════════════════════╬══════════════════════════════════════════╣"
echo "║ Cart — Swagger           ║ http://localhost:8086/swagger-ui.html     ║"
echo "║ Cart — vía Gateway       ║ http://localhost:8080/cart/cart-item      ║"
echo "╠══════════════════════════╬══════════════════════════════════════════╣"
echo "║ Invoice — Swagger        ║ http://localhost:8085/swagger-ui.html     ║"
echo "║ Invoice — vía Gateway    ║ http://localhost:8080/invoice/invoice     ║"
echo "╚══════════════════════════╩══════════════════════════════════════════╝"
echo -e "${NC}"
echo -e "  Logs individuales en: ${CYAN}$LOGS_DIR/${NC}"
echo -e "  Presiona ${BOLD}Ctrl+C${NC} para detener todos los servicios."
echo ""

# Mantener script activo
wait
