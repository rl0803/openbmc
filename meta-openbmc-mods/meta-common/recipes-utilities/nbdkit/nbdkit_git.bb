SUMMARY = "nbdkit is a toolkit for creating NBD servers."
DESCRIPTION = "NBD — Network Block Device — is a protocol \
for accessing Block Devices (hard disks and disk-like things) \
over a Network. \
\
nbdkit is a toolkit for creating NBD servers."

HOMEPAGE = "https://github.com/libguestfs/nbdkit"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f9dcc2d8acdde215fa4bd6ac12bb14f0"

SRC_URI = "git://github.com/libguestfs/nbdkit.git;protocol=https"
SRC_URI += "file://0001-Force-nbdkit-to-send-PATCH-as-upload-method.patch"
SRC_URI += "file://0002-Add-support-for-ssl-config.patch"

PV = "1.25.5+git${SRCPV}"
SRCREV = "c828c6d48ff6b69454cad98054a1920d03c4b4c7"

S = "${WORKDIR}/git"

DEPENDS = "curl xz e2fsprogs zlib"

inherit pkgconfig python3native perlnative autotools
inherit autotools-brokensep

# Specify any options you want to pass to the configure script using EXTRA_OECONF:
EXTRA_OECONF = "--disable-python --disable-perl --disable-ocaml \
               --disable-rust --disable-ruby --disable-tcl \
               --disable-lua --disable-vddk --without-libvirt \
               --without-libguestfs"

do_install_append() {
    rm -f ${D}/usr/share/bash-completion/completions/nbdkit
    rmdir ${D}/usr/share/bash-completion/completions
    rmdir ${D}/usr/share/bash-completion
}
