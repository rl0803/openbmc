SUMMARY = "Special mode manager daemon to handle manufacturing modes"
DESCRIPTION = "Daemon exposes the manufacturing mode property"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

SRC_URI = "git://github.com/Intel-BMC/special-mode-manager.git;protocol=ssh"
SRCREV = "5f413eec673f3e6e5e754e2c55048abf0a146e5b"

EXTRA_OECMAKE += "${@bb.utils.contains('EXTRA_IMAGE_FEATURES', 'validation-unsecure', '-DBMC_VALIDATION_UNSECURE_FEATURE=ON', '', d)}"

inherit cmake systemd
SYSTEMD_SERVICE_${PN} = "specialmodemgr.service"

DEPENDS += " \
    systemd \
    sdbusplus \
    phosphor-logging \
    boost \
    libpam \
    "
RDEPENDS_${PN} += " \
    libsystemd \
    sdbusplus \
    phosphor-logging \
    "
