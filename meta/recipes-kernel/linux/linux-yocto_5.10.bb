KBRANCH ?= "v5.10/standard/base"

require recipes-kernel/linux/linux-yocto.inc

# board specific branches
KBRANCH:qemuarm  ?= "v5.10/standard/arm-versatile-926ejs"
KBRANCH:qemuarm64 ?= "v5.10/standard/qemuarm64"
KBRANCH:qemumips ?= "v5.10/standard/mti-malta32"
KBRANCH:qemuppc  ?= "v5.10/standard/qemuppc"
KBRANCH:qemuriscv64  ?= "v5.10/standard/base"
KBRANCH:qemuriscv32  ?= "v5.10/standard/base"
KBRANCH:qemux86  ?= "v5.10/standard/base"
KBRANCH:qemux86-64 ?= "v5.10/standard/base"
KBRANCH:qemumips64 ?= "v5.10/standard/mti-malta64"

SRCREV_machine:qemuarm ?= "765d9bff031ec7198a3e5359127249df19b8bdfa"
SRCREV_machine:qemuarm64 ?= "c1bb5868e46a792e176b842aefc98dea2727e5fa"
SRCREV_machine:qemumips ?= "63624e5dfe27ae4f9caa4a70b12eec4e63a9e557"
SRCREV_machine:qemuppc ?= "d054e2117a15fe0155fde3878811cf233fca3f83"
SRCREV_machine:qemuriscv64 ?= "7a88f25e2d0e2dd2da6f0e9cab91e0d097b9e10a"
SRCREV_machine:qemuriscv32 ?= "7a88f25e2d0e2dd2da6f0e9cab91e0d097b9e10a"
SRCREV_machine:qemux86 ?= "7a88f25e2d0e2dd2da6f0e9cab91e0d097b9e10a"
SRCREV_machine:qemux86-64 ?= "7a88f25e2d0e2dd2da6f0e9cab91e0d097b9e10a"
SRCREV_machine:qemumips64 ?= "d435de927eb2adb0cfcd49572bb71ce7ff31035d"
SRCREV_machine ?= "7a88f25e2d0e2dd2da6f0e9cab91e0d097b9e10a"
SRCREV_meta ?= "5bdb3bf9619c5f2b928fc784aa0082d399d83fbb"

# remap qemuarm to qemuarma15 for the 5.8 kernel
# KMACHINE:qemuarm ?= "qemuarma15"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.10;destsuffix=${KMETA}"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION ?= "5.10.84"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"
DEPENDS += "gmp-native"

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"

KERNEL_DEVICETREE:qemuarmv5 = "versatile-pb.dtb"

COMPATIBLE_MACHINE = "qemuarm|qemuarmv5|qemuarm64|qemux86|qemuppc|qemuppc64|qemumips|qemumips64|qemux86-64|qemuriscv64|qemuriscv32"

# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc"
KERNEL_FEATURES:append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES:append:qemuall=" cfg/virtio.scc features/drm-bochs/drm-bochs.scc"
KERNEL_FEATURES:append:qemux86=" cfg/sound.scc cfg/paravirt_kvm.scc"
KERNEL_FEATURES:append:qemux86-64=" cfg/sound.scc cfg/paravirt_kvm.scc"
KERNEL_FEATURES:append = " ${@bb.utils.contains("TUNE_FEATURES", "mx32", " cfg/x32.scc", "", d)}"
KERNEL_FEATURES:append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/scsi/scsi-debug.scc", "", d)}"
KERNEL_FEATURES:append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/gpio/mockup.scc", "", d)}"
