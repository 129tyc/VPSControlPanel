package com.tyc129.vpscontrolpanel.data.api.kiwivm


import android.content.Context
import com.tyc129.vpscontrolpanel.data.api.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Code on 2017/12/4 0004.
 * @author 谈永成
 * @version 1.0
 */
class VendorKiwiVM(context: Context) : ApiVendor {

    companion object {
        private const val DEFAULT_TIMEOUT = 10000L
        private const val URL_API = "https://api.64clouds.com/"
    }


    private val engine = initEngine()
    private val apiList = initApiList()
    private val apiFuncList = initFuncList(context)


    private fun initEngine(): ApiEngine {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        return EngineKiwiVM(Retrofit
                .Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL_API)
                .build()
                .create(ServiceKiwiVM::class.java))
    }

    private fun initApiList(): List<Api> {
        val result = mutableListOf<Api>()
        result.add(Api("1",
                "Start",
                "Starts the VPS",
                "start",
                listOf(),
                listOf()))
        result.add(Api("2",
                "Stop",
                "Stops the VPS",
                "stop",
                listOf(),
                listOf()))
        result.add(Api("3",
                "Restart",
                "Reboots the VPS",
                "restart",
                listOf(),
                listOf()))
        result.add(Api("4",
                "Kill",
                "Allows to forcibly stop a VPS that is stuck and cannot be stopped by normal means. Please use this feature with great care as any unsaved data will be lost.",
                "kill",
                listOf(),
                listOf()))
        result.add(Api("5",
                "GetServiceInfo",
                "Get Service Information",
                "getServiceInfo",
                listOf(),
                listOf(ApiParameter("vm_type", "VMType", "Hypervizor type (ovz or kvm)"),
                        ApiParameter("hostname", "HostName", "Hostname of the VPS"),
                        ApiParameter("node_ip", "NodeIp", "IP address of the physical node"),
                        ApiParameter("node_alias", "NodeAlias", "Internal nickname of the physical node"),
                        ApiParameter("node_location", "NodeLocation", "Physical location (country, state)"),
                        ApiParameter("location_ipv6_ready", "LocationIPv6Ready", "Whether IPv6 is supported at the current location"),
                        ApiParameter("plan", "Plan", "Name of plan"),
                        ApiParameter("plan_monthly_data", "PlanMonthlyData", "Allowed monthly data transfer (bytes)"),
                        ApiParameter("plan_disk", "PlanDisk", "Disk quota (bytes)"),
                        ApiParameter("plan_ram", "PlanRam", "RAM (bytes)"),
                        ApiParameter("plan_swap", "PlanSWAP", "SWAP (bytes)"),
                        ApiParameter("os", "OS", "Operating system"),
                        ApiParameter("email", "E-mail", "Primary e-mail address of the account"),
                        ApiParameter("data_counter", "DataCounter", "Data transfer used in the current billing month"),
                        ApiParameter("data_next_reset", "DataNextReset", "Date and time of transfer counter reset (UNIX timestamp)"),
                        ApiParameter("ip_addresses", "IPAddress", "IPv4 and IPv6 addresses assigned to VPS (Array)"),
                        ApiParameter("plan_max_ipv6s", "PlanMaxIPv6s", "Maximum number of IPv6 addresses allowed by plan"),
                        ApiParameter("rdns_api_available", "rDNSAPIAvailable", "Whether or not rDNS records can be set via API"),
                        ApiParameter("ptr", "PTR", "rDNS records (Array of two-dimensional arrays: ip=>value)"),
                        ApiParameter("suspended", "Suspended", "Whether VPS is suspended"))))
        result.add(Api("6",
                "GetLiveServiceInfo",
                "This function returns all data provided by getServiceInfo. In addition, it provides detailed status of the VPS. \n" +
                        "Please note that this call may take up to 15 seconds to complete. \n" +
                        "\n" +
                        "Depending on hypervisor this call will return the following information: \n" +
                        "\n",
                "getLiveServiceInfo",
                listOf(),
                listOf(
                        ApiParameter("vz_status", "OVZStatus", "array containing OpenVZ beancounters, system load average, number of processes, open files, sockets, memory usage etc"),
                        ApiParameter("vz_quota", "OVZQuota", "array containing OpenVZ disk size, inodes and usage info"),
                        ApiParameter("is_cpu_throttled", "IsCPUThrottled", "0 = CPU is not throttled, 1 = CPU is throttled due to high usage. Throttling resets automatically every 2 hours."),
                        ApiParameter("ssh_port", "SSHPort", "SSH port of the VPS"),
                        ApiParameter("ve_status", "KVMStatus", "Running or Stopped"),
                        ApiParameter("ve_mac1", "KVMMac", "MAC address of primary network interface"),
                        ApiParameter("ve_used_disk_space_b", "KVMUsedDiskSpace(Byte)", "Occupied (mapped) disk space in bytes"),
                        ApiParameter("ve_disk_quota_gb", "KVMDiskQuota(GB)", "Actual size of disk image in GB"))))
        result.add(Api("7",
                "GetAvailableOS",
                "Get Available OS",
                "getAvailableOS",
                listOf(),
                listOf(ApiParameter("installed", "Installed", "Currently installed Operating System"),
                        ApiParameter("templates", "Templates", "Array of available OS"))))
        result.add(Api("8",
                "ReinstallOS",
                "Reinstall the Operating System. OS must be specified via \"os\" variable. Use getAvailableOS call to get list of available systems.",
                "reinstallOS",
                listOf(ApiParameter("os", "OS", "Operating system")),
                listOf()))
        result.add(Api("9",
                "ResetRootPassword",
                "Generates and sets a new root password. ",
                "resetRootPassword",
                listOf(),
                listOf(ApiParameter("password", "Password", "New root password"))))
        result.add(Api("10",
                "GetUsageGraphs",
                "Obsolete, use getRawUsageStats instead",
                "getUsageGraphs",
                listOf(),
                listOf()))
        result.add(Api("11",
                "GetRawUsageStats",
                "Returns a two-dimensional array with the detailed usage statistics shown under Detailed Statistics in KiwiVM.",
                "getRawUsageStats",
                listOf(),
                listOf(ApiParameter("data", "Data", "Usage Data"))))
        result.add(Api("12",
                "SetHostname",
                "(OVZ only) Sets new hostname.",
                "setHostname",
                listOf(ApiParameter("newHostname", "NewHostname", "New Hostname")),
                listOf()))
        result.add(Api("13",
                "SetPTR",
                "Sets new PTR (rDNS) record for IP.",
                "setPTR",
                listOf(ApiParameter("ip", "IP", "IP"),
                        ApiParameter("ptr", "PTR", "PTR")),
                listOf()))
        result.add(Api("14",
                "Restart",
                "Reboots the VPS",
                "restart",
                listOf(),
                listOf()))
        result.add(Api("15",
                "BasicShell-CD",
                "Simulate change of directory inside of the VPS. Can be used to build a shell like Basic shell. ",
                "basicShell/cd",
                listOf(ApiParameter("currentDir", "CurrentDir", "Current Directory"),
                        ApiParameter("newDir", "NewDir", "New Directory")),
                listOf(ApiParameter("pwd", "PWD", ": Result of the \"pwd\" command after the change."))))
        result.add(Api("16",
                "BasicShell-Exec",
                "Execute a shell command on the VPS (synchronously). ",
                "basicShell/exec",
                listOf(ApiParameter("command", "Command", "Command to Execute")),
                listOf(ApiParameter("error", "Error", "Exit status code of the executed command "),
                        ApiParameter("message", "Message", "Console output of the executed command"))))
        result.add(Api("17",
                "ShellScript-Exec",
                "Execute a shell script on the VPS (asynchronously). ",
                "shellScript/exec",
                listOf(ApiParameter("script", "Script", "Script to Execute")),
                listOf(ApiParameter("log", "Log", "Name of the output log file."))))
        result.add(Api("18",
                "Snapshot-Create",
                "Create snapshot ",
                "snapshot/create",
                listOf(ApiParameter("description", "Description", "Description (optional)")),
                listOf(ApiParameter("notificationEmail", "NotificationEmail", "E-mail address on file where notification will be sent once task is completed."))))
        result.add(Api("19",
                "Snapshot-List",
                "Get list of snapshots.",
                "snapshot/list",
                listOf(),
                listOf(ApiParameter("snapshots", "Snapshots", "Array of snapshots (fileName, os, description, size, md5, sticky, purgesIn, downloadLink)."))))
        result.add(Api("20",
                "snapshot-Delete",
                "Delete snapshot by fileName (can be retrieved with snapshot/list call).",
                "snapshot/delete",
                listOf(ApiParameter("snapshot", "Snapshot", "Snapshot FileName need Delete")),
                listOf()))
        result.add(Api("21",
                "Snapshot-Restore",
                "Restores snapshot by fileName (can be retrieved with snapshot/list call). This will overwrite all data on the VPS.",
                "snapshot/restore",
                listOf(),
                listOf()))
        result.add(Api("22",
                "Snapshot-ToggleSticky",
                "Set or remove sticky attribute (\"sticky\" snapshots are never purged). Name of snapshot can be retrieved with snapshot/list call – look for fileName variable.",
                "snapshot/toggleSticky",
                listOf(ApiParameter("snapshot", "Snapshot", "Snapshot FileName"),
                        ApiParameter("sticky", "Sticky", "Set sticky = 1 to set sticky attribute,Set sticky = 0 to remove sticky attribute")),
                listOf()))
        result.add(Api("23",
                "Snapshot-Export",
                "Generates a token with which the snapshot can be transferred to another instance.",
                "snapshot/export",
                listOf(ApiParameter("snapshot", "Snapshot", "Snapshot FileName")),
                listOf()))
        result.add(Api("24",
                "Snapshot-import",
                "Imports a snapshot from another instance identified by VEID and Token. Both VEID and Token must be obtained from another instance beforehand with a snapshot/export call.",
                "snapshot/import",
                listOf(ApiParameter("sourceVeid", "SourceVeid", "Source Veid"),
                        ApiParameter("sourceToken", "SourceToken", "Source Token")),
                listOf()))
        result.add(Api("25",
                "IPv6-Add",
                "Assigns a new IPv6 address. For initial IPv6 assignment an empty IP is required (call without parameters), and a new IP from the available pool is assigned automatically. All subsequent requested IPv6 addresses must be within the /64 subnet of the first IPv6 address.",
                "ipv6/add",
                listOf(ApiParameter("ip", "IP", "IP")),
                listOf(ApiParameter("ip", "IP", "Newly assigned IPv6 address"))))
        result.add(Api("26",
                "IPv6-Delete",
                "Releases specified IPv6 address.",
                "ipv6/delete",
                listOf(ApiParameter("ip", "IP", "IP")),
                listOf()))
        result.add(Api("27",
                "Migrate-GetLocations",
                "Return all possible migration locations.",
                "migrate/getLocations",
                listOf(),
                listOf(ApiParameter("currentLocation", "CurrentLocation", "ID of current location"),
                        ApiParameter("locations", "Locations", "IDs of locations available for migration into"),
                        ApiParameter("descriptions", "Descriptions", "Friendly descriptions of available locations"))))
        result.add(Api("28",
                "Migrate-Start",
                "Start VPS migration to new location. Takes new location ID as input. Note that this will result in all IPv4 addresses to be replaced with new ones, and all IPv6 addresses will be released. ",
                "migrate/start",
                listOf(),
                listOf(ApiParameter("notificationEmail", "NotificationEmail", "E-mail address on file where notification will be sent once task is completed."),
                        ApiParameter("newIps", "NewIPs", "Array of new IP addresses assigned to the VPS."))))
        result.add(Api("29",
                "CloneFromExternalServer",
                "(OVZ only) Clone a remote server or VPS. See Migrate from another server for example on how this works.",
                "cloneFromExternalServer",
                listOf(ApiParameter("externalServerIP", "ExternalServerIP", "ExternalServerIP"),
                        ApiParameter("externalServerSSHport", "ExternalServerSSHport", "ExternalServerSSHport"),
                        ApiParameter("externalServerRootPassword", "ExternalServerRootPassword", "ExternalServerRootPassword")),
                listOf()))

        result.add(Api("30",
                "GetSuspensionDetails",
                "Retrieve information related to service suspensions.",
                "getSuspensionDetails",
                listOf(),
                listOf(ApiParameter("suspension_count", "SuspensionCount", "Number of times service was suspended in current calendar year"),
                        ApiParameter("suspensions", "Suspensions", "array of all outstanding issues along with supporing evidence of abuse. See example below."),
                        ApiParameter("evidence", "Evidence", "Full text of the complaint or more details about the issue"))))
        result.add(Api("31",
                "Unsuspend",
                "Clear abuse issue identified by record_id and unsuspend the VPS. Refer to getSuspensionDetails call for details.",
                "unsuspend",
                listOf(ApiParameter("record", "Record", "Record")),
                listOf()))
        result.add(Api("32",
                "GetRateLimitStatus",
                "When you perform too many API calls in a short amount of time, KiwiVM API may start dropping your requests for a few minutes. This call allows monitoring this matter.",
                "getRateLimitStatus",
                listOf(),
                listOf(ApiParameter("remaining_points_15min", "RemainingPoints(15min)", "Number of \"points\" available to use in the current 15-minute interval"),
                        ApiParameter("remaining_points_24h", "RemainingPoints(24h)", "Number of \"points\" available to use in the current 24-hour interval"))))
        return result
    }

    private fun initFuncList(context: Context): List<ApiFunc> {
        return listOf(ApiFuncDataCounter(context, engine, this))
    }

    override fun applyEngine() = engine

    override fun applyApiList() = apiList

    override fun applyFuncList() = apiFuncList

    override fun requireApi(aId: String) = apiList.firstOrNull { aId == it.aId }
}