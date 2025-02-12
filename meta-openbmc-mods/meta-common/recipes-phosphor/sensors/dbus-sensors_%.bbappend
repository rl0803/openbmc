FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PROJECT_SRC_DIR := "${THISDIR}/${PN}"

SRCREV = "5cf0f99210890d0cd52d5929e919316d238a5701"
#SRC_URI = "git://github.com/openbmc/dbus-sensors.git"

SRC_URI += "\
    file://intrusionsensor-depend-on-networkd.conf \
    file://0001-Add-check-for-min-max-received-from-hwmon-files.patch \
    file://0002-Fix-PECI-client-creation-flow.patch \
    file://0003-Fix-missing-threshold-de-assert-event-when-threshold.patch \
    file://0004-Fan-Tach-Sensor-Threshold-Ignore-Zero.patch \
    file://0005-Fix-PECI-ioctl-number.patch \
    file://0006-CPUSensor-create-RequirediTempSensor-if-defined.patch \
    file://0007-Add-support-for-the-energy-hwmon-type.patch \
    file://0008-CPUSensor-additional-debug-message.patch \
    "

DEPENDS_append = " libgpiod libmctp"

PACKAGECONFIG += " \
    adcsensor \
    cpusensor \
    exitairtempsensor \
    fansensor \
    hwmontempsensor \
    intrusionsensor \
    ipmbsensor \
    mcutempsensor \
    psusensor \
"

PACKAGECONFIG[nvmesensor] = "-Dnvme=enabled, -Dnvme=disabled"

# Enable Validation unsecure based on IMAGE_FEATURES
EXTRA_OEMESON += "${@bb.utils.contains('EXTRA_IMAGE_FEATURES', 'validation-unsecure', '-Dvalidate-unsecure-feature=enabled', '', d)}"

SYSTEMD_SERVICE_${PN} += "${@bb.utils.contains('PACKAGECONFIG', 'nvmesensor', \
                                               'xyz.openbmc_project.nvmesensor.service', \
                                               '', d)}"

do_install_append() {
    svc="xyz.openbmc_project.intrusionsensor.service"
    srcf="${WORKDIR}/intrusionsensor-depend-on-networkd.conf"
    dstf="${D}/etc/systemd/system/${svc}.d/10-depend-on-networkd.conf"
    mkdir -p "${D}/etc/systemd/system/${svc}.d"
    install "${srcf}" "${dstf}"
}
