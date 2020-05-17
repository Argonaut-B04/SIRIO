import React from "react";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import PollingService from "../../Services/PollingService";
import { withRouter } from "react-router-dom";
import SirioBarChart from "../../Components/Chart/SirioBarChart";
import SirioDashboardBox from "../../Components/Box/SirioDashboardBox";
import DashboardStaffService from '../../Services/DashboardStaffService';
import { Redirect, NavLink } from 'react-router-dom';
import SirioField from "../../Components/Form/SirioFormComponent/SirioField";
import SirioComponentHeader from "../../Components/Header/SirioComponentHeader";
import EmployeeService from "../../Services/EmployeeService";
import SirioButton from '../../Components/Button/SirioButton';
import SirioForm from '../../Components/Form/SirioForm';

/**
 * Controller untuk menampilkan halaman utama
 */
class DashboardStaff extends React.Component {

    constructor(props) {
        super(props);

        // state untuk menjalankan preloader saat halaman diakses
        this.state = {
            jumlahRekomendasi: "",
            jumlahTemuan: "",
            jumlahRekomendasiOverdue: "",
            jumlahRekomendasiBelumDiimplementasi: "",
            jumlahRekomendasiDiimplementasi: "",
            listTemuan: [],
            listRekomendasiOverdue: [],
            listRekomendasiBelumDiimplementasi: [],
            listRekomendasiDiimplementasi: [],
            listMonth: [],
            optionList: [],
            qa: "",
            namaqa: "",
            tanggalAwal: "",
            tanggalAkhir: "",
            preloader: true,
            contentLoading: !PollingService.isConnected()
        }
        this.renderRows = this.renderRows.bind(this);
        this.renderOption = this.renderOption.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.submitable = this.submitable.bind(this);
    }

    async renderRows() {
        if (this.state.qa === null || this.state.qa === "" || this.state.qa === 0) {
            DashboardStaffService.getAllData(this.state.tanggalAwal, this.state.tanggalAkhir).then(response => {
                this.setState({
                    jumlahRekomendasi: response.data.result.jumlahRekomendasi,
                    jumlahTemuan: response.data.result.jumlahTemuan,
                    jumlahRekomendasiOverdue: response.data.result.jumlahRekomendasiOverdue,
                    jumlahRekomendasiBelumDiimplementasi: response.data.result.jumlahRekomendasiBelumDiimplementasi,
                    jumlahRekomendasiDiimplementasi: response.data.result.jumlahRekomendasiDiimplementasi,
                    listTemuan: response.data.result.listTemuan,
                    listRekomendasiOverdue: response.data.result.listRekomendasiOverdue,
                    listRekomendasiBelumDiimplementasi: response.data.result.listRekomendasiBelumDiimplementasi,
                    listRekomendasiDiimplementasi: response.data.result.listRekomendasiDiimplementasi,
                    listMonth: response.data.result.listMonth,
                    namaqa: "",
                    qa: 0
                })
            })
            .catch(error => {
                if (error.response.data.status === 401) {
                    this.setState({
                        redirector: <Redirect to={{
                            pathname: "/401",
                            state: {
                                detail: error.response.data.message,
                            }
                        }} />
                    })
                }
            })
            ;
        } else if (this.state.qa !== null || this.state.qa !== "") {
            DashboardStaffService.getDashboardQA(this.state.qa, this.state.tanggalAwal, this.state.tanggalAkhir).then(response => {
                this.setState({
                    jumlahRekomendasi: response.data.result.jumlahRekomendasi,
                    jumlahTemuan: response.data.result.jumlahTemuan,
                    jumlahRekomendasiOverdue: response.data.result.jumlahRekomendasiOverdue,
                    jumlahRekomendasiBelumDiimplementasi: response.data.result.jumlahRekomendasiBelumDiimplementasi,
                    jumlahRekomendasiDiimplementasi: response.data.result.jumlahRekomendasiDiimplementasi,
                    listTemuan: response.data.result.listTemuan,
                    listRekomendasiOverdue: response.data.result.listRekomendasiOverdue,
                    listRekomendasiBelumDiimplementasi: response.data.result.listRekomendasiBelumDiimplementasi,
                    listRekomendasiDiimplementasi: response.data.result.listRekomendasiDiimplementasi,
                    listMonth: response.data.result.listMonth,
                    namaqa: response.data.result.namaqa
                })
            })
            .catch(error => {
                if (error.response.data.status === 401) {
                    this.setState({
                        redirector: <Redirect to={{
                            pathname: "/401",
                            state: {
                                detail: error.response.data.message,
                            }
                        }} />
                    })
                }
            })
            ;
        }
    }

    // jika sudah selesai dirender, hentikan preloader
    componentDidMount() {
        this.renderRows();
        this.renderOption();
        if (!PollingService.isConnected()) {
            this.setState({
                preloader: false,
                contentLoading: true,
                loadingBody: "Mencoba untuk menghubungi server, harap tunggu sebentar"
            })

            PollingService.pollServer()
                .then(response => {
                    this.setState({
                        loadingBody: response.data.result
                    })

                    PollingService.connected();

                    setTimeout(function () { // Memberikan jeda waktu 0.5 detik
                        this.setState({
                            contentLoading: false
                        })
                    }.bind(this), 500)
                })
                .catch(error => {
                    this.setState({
                        loadingBody: "Gagal menghubungi server, harap refresh halaman ini"
                    })
                })
        } else {
            this.setState({
                preloader: false,
            })
        }
    }

    submitable() {
        if (this.state.tanggalAwal !== "") {
            return this.state.tanggalAkhir !== "";
        } else {
            return this.state.qa !== "" || this.state.tanggalAkhir !== "";
        }
    }

    getBetween() {
        const { errorTanggal} = this.state;
        var tombolSimpan =
        <SirioButton
            purple
            disabled
            classes="p-1"
        >
            Cari
        </SirioButton>;
        if (this.submitable()) {
            tombolSimpan =
                <SirioButton
                    purple
                    recommended
                    classes="p-1"
                    onClick={this.renderRows}
                >
                    Cari
                </SirioButton>
        }
        return (
            <div className="row">
                <div className="col-md-12">
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChange}
                        classes="p-1"
                        name="qa"
                        value={this.state.qa}
                        optionList={this.state.optionList}
                        label=" Filter QA"
                    />
                </div>
                <div className="col-md-12 pl-0">
                    <SirioField
                            type="date"
                            handleChange={this.handleChange}
                            classes="p-1"
                            name="tanggalAwal"
                            value={this.state.tanggalAwal}
                            label="Tanggal Awal"
                        />
                </div>
                <div className="col-md-12 pl-0">
                    <SirioField
                            type="date"
                            handleChange={this.handleChange}
                            disabled={this.state.tanggalAwal === ""}
                            classes="p-1"
                            min={this.state.tanggalAwal}
                            required={this.state.tanggalAwal !== ""}
                            name="tanggalAkhir"
                            value={this.state.tanggalAkhir}
                            label="Tanggal Akhir"

                        />
                </div>
                <div className="col-md-9 pl-0 mt-3">
                {tombolSimpan}
                </div>
                {/* <div className="col-md-12 mr-0 pl-0">
                    <SirioField
                            type="date"
                            handleChange={this.handleChange}
                            classes="p-1"
                            name="tanggalAwal"
                            value={this.state.tanggalAwal}
                            label="Tanggal Awal"
                        />
                </div> */}
            </div>
        )
        // return (
        //     [
        //         {
        //             handleChange: this.handleSelectChange,
        //             type: "select",
        //             name: "qa",
        //             value: this.state.qa,
        //             optionList: this.state.optionList
        //         }, {
        //             handleChange: this.handleSelectChange,
        //             type: "date",
        //             name: "tanggalAwal",
        //             value: this.state.tanggalAwal
        //         }, {
        //             handleChange: this.handleSelectChange,
        //             type: "date",
        //             name: "tanggalAkhir",
        //             value: this.state.tanggalAkhir
        //         }
        //     ]
        // )
    }

    getButton() {
        
        return (
            <div className="row">
                
            </div>
        )
    }
    async renderOption() {
        const response = await EmployeeService.getAllQAOfficer();

        const optionList = response.data.result.map(qa => {
            return (
                {
                    label: qa.nama,
                    value: qa.idEmployee
                }
            )
        });

        this.setState({
            optionList: optionList
        })
    }

    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    handleChange(event) {
        const { name, value } = event.target;
        if (name === "tanggalAwal") {
            this.setState({
                tanggalAkhir: ""
            })
        }
        this.setState(
            {
                [name]
                    : value
            }
        );
    }

    // handleSubmit(event) {
    //     // event.preventDefault wajib ada
    //     event.preventDefault();
    //         const dashboard = {
    //             qa: this.state.qa,
    //             tanggalAwal: this.state.tanggalAwal,
    //             tanggalAkhir: this.state.tanggalAkhir
    //         }
    //         RegistrasiRisikoService.submitChanges(dashboard)
    //             .then(() => this.setRedirect());
    // }

    render() {
        const { preloader, contentLoading, loadingBody } = this.state;
        const data = {
            labels: this.state.listMonth,
            datasets: [
                {
                    label: 'Jumlah Temuan',
                    backgroundColor: '#7F3F98',
                    borderColor: '#7F3F98',
                    stack: 'Stack 0',
                    borderWidth: 1,
                    hoverBackgroundColor: '#7F3F98',
                    hoverBorderColor: '#7F3F98',
                    data: this.state.listTemuan
                },
                {
                    label: 'Jumlah Rekomendasi Diimplementasi',
                    backgroundColor: '#5DBCD2',
                    borderColor: '#5DBCD2',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    hoverBackgroundColor: '#5DBCD2',
                    hoverBorderColor: '#5DBCD2',
                    data: this.state.listRekomendasiDiimplementasi
                },
                {
                    label: 'Jumlah Rekomendasi Belum Diimplementasi',
                    stack: 'Stack 1',
                    backgroundColor: 'rgba(255, 0, 0, 0.85)',
                    borderColor: 'rgba(255, 0, 0, 0.85)',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(255, 0, 0, 0.85)',
                    hoverBorderColor: 'rgba(255, 0, 0, 0.85)',
                    data: this.state.listRekomendasiBelumDiimplementasi
                },
                {
                    label: 'Jumlah Rekomendasi Overdue',
                    stack: 'Stack 1',
                    backgroundColor: '#F2C94C',
                    borderColor: '#F2C94C',
                    borderWidth: 1,
                    hoverBackgroundColor: '#F2C94C',
                    hoverBorderColor: '#F2C94C',
                    data: this.state.listRekomendasiOverdue
                }
            ]
        };

        const boxData = [
            {
                title: "Rekomendasi",
                value: this.state.jumlahRekomendasi
            },
            {
                title: "Rekomendasi Diimplementasi",
                value: this.state.jumlahRekomendasiDiimplementasi === "NaN" ? "-" : this.state.jumlahRekomendasiDiimplementasi + "%"
            },
            {
                title: "Rekomendasi Belum Diimplementasi",
                value: this.state.jumlahRekomendasiBelumDiimplementasi === "NaN" ? "-" : this.state.jumlahRekomendasiBelumDiimplementasi + "%"
            },
            {
                title: "Rekomendasi Overdue",
                value: this.state.jumlahRekomendasiOverdue === "NaN" ? "-" : this.state.jumlahRekomendasiOverdue + "%"
            },
            {
                title: "Temuan",
                value: this.state.jumlahTemuan
            }
        ]
    
        return (
            <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                <h1 className="text-left">Dashboard Performa Staff Operational Risk {this.state.namaqa}</h1>
                <br/>
                <SirioComponentHeader
                    betweenTitleSubtitle={this.getBetween()}
                />
                {/* <SirioForm
                    inputDefinition={this.getBetween()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                /> */}
                <div>
                    <SirioBarChart data={data} />
                    <h6 className="text-right pt-3">Data ini hanya data sementara</h6>
                </div>

                <div>
                    <SirioDashboardBox data={boxData} />
                </div>
            </SirioMainLayout>
        )
    }
}

export default withRouter(DashboardStaff);