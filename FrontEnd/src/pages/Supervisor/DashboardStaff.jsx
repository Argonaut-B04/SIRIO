import React from "react";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import PollingService from "../../Services/PollingService";
import { withRouter } from "react-router-dom";
import SirioBarChart from "../../Components/Chart/SirioBarChart";
import SirioDashboardBox from "../../Components/Box/SirioDashboardBox";
import DashboardStaffService from '../../Services/DashboardStaffService';
import { Redirect } from 'react-router-dom';
import SirioField from "../../Components/Form/SirioFormComponent/SirioField";
import SirioComponentHeader from "../../Components/Header/SirioComponentHeader";
import EmployeeService from "../../Services/EmployeeService";
import SirioButton from '../../Components/Button/SirioButton';
import moment from 'moment';
import AuthenticationService from "Services/AuthenticationService";

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
            contentLoading: true,
            max: "",
            role: AuthenticationService.getRole(),
            preloader: true,
            contentLoading: !PollingService.isConnected()
        }
        // Jangan lupa bind seluruh fungsi yang menggunakan state
        this.contentFinishLoading = this.contentFinishLoading.bind(this);
        this.contentStartLoading = this.contentStartLoading.bind(this);
        this.changeLoadingBody = this.changeLoadingBody.bind(this);
        this.renderRows = this.renderRows.bind(this);
        this.renderOption = this.renderOption.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.submitable = this.submitable.bind(this);
    }

    // Fungsi untuk menghentikan tampilan loader konten
    contentFinishLoading() {
        setTimeout(function () { // Memberikan jeda waktu 0.5 detik
            this.setState({
                contentLoading: false
            })
        }.bind(this), 500)
    }

    // Fungsi untuk menampilkan loader konten
    contentStartLoading() {
        this.setState({
            contentLoading: true
        })
    }

    // Fungsi untuk mengubah teks loader konten
    changeLoadingBody(body) {
        this.setState({
            loadingBody: body
        })
    }

    async renderRows() {
        this.contentStartLoading();
        this.changeLoadingBody("Mengambil data dari server");
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
                }, this.contentFinishLoading())
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
                }, this.contentFinishLoading())
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
        } else if (this.state.role === "QA Officer Operational Risk") {
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
                    namaqa: response.data.result.namaqa
                }, this.contentFinishLoading())
            })
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
        var max = moment(this.state.tanggalAwal).add(2, 'y')
        max = max.subtract(1, 'd')
        max = max.format("YYYY-MM-DD")
        if (this.state.role === "QA Officer Operational Risk") {
            return (
                <div>
                    <div className="col-md-4 pl-0">
                        <SirioField
                                type="date"
                                handleChange={this.handleChange}
                                classes="p-1"
                                name="tanggalAwal"
                                value={this.state.tanggalAwal}
                            />
                    </div>
                    <div className="col-md-4 pl-0">
                        <SirioField
                                type="date"
                                handleChange={this.handleChange}
                                disabled={this.state.tanggalAwal === ""}
                                classes="p-1"
                                min={this.state.tanggalAwal}
                                max={max}
                                required={this.state.tanggalAwal !== ""}
                                name="tanggalAkhir"
                                value={this.state.tanggalAkhir}
    
                            />
                    </div>
                    <div className="col-md-4 pl-0 mt-3">
                    {tombolSimpan}
                    </div>
                </div>
            )
        }
        if (this.state.role === "Supervisor") {
        return (
            <div className="row">
                <div className="col-md-3 mb-1 pl-0">
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChange}
                        classes="p-1"
                        name="qa"
                        value={this.state.qa}
                        optionList={this.state.optionList}
                    />
                </div>
                <div className="col-md-3 pl-0">
                    <SirioField
                            type="date"
                            handleChange={this.handleChange}
                            classes="p-1"
                            name="tanggalAwal"
                            value={this.state.tanggalAwal}
                        />
                </div>
                <div className="col-md-3 pl-0">
                    <SirioField
                            type="date"
                            handleChange={this.handleChange}
                            disabled={this.state.tanggalAwal === ""}
                            classes="p-1"
                            min={this.state.tanggalAwal}
                            max={max}
                            required={this.state.tanggalAwal !== ""}
                            name="tanggalAkhir"
                            value={this.state.tanggalAkhir}

                        />
                </div>
                <div className="col-md-3 pl-0 mt-3">
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
    }

    getButton() {
        
        return (
            <div className="row">
                
            </div>
        )
    }
    async renderOption() {
        this.contentStartLoading();
        this.changeLoadingBody("Mencoba untuk masuk");
        const response = await EmployeeService.getAllQAOfficerDD();

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
        }, this.contentFinishLoading())
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
        if (this.state.role === "Administrator"
        || this.state.role === "Manajer Operational Risk"
        || this.state.role === "Branch Manager") {
            return (
                <Redirect to={{
                    pathname: "/error",
                    state: {
                        detail: "Not Authorized",
                        code: "401"
                    }
                }} />
            )
        }
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
                    <SirioBarChart data={data} contentFinishLoading={this.contentFinishLoading} contentStartLoading={this.contentStartLoading} changeLoadingBody={this.changeLoadingBody}/>
                    <h6 className="text-right pt-3">Data ini hanya data sementara</h6>
                </div>

                <div>
                    <SirioDashboardBox data={boxData} contentFinishLoading={this.contentFinishLoading} contentStartLoading={this.contentStartLoading} changeLoadingBody={this.changeLoadingBody}/>
                </div>
            </SirioMainLayout>
        )
    }
}

export default withRouter(DashboardStaff);