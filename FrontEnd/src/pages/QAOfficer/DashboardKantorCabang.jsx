import React from "react";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import PollingService from "../../Services/PollingService";
import { withRouter } from "react-router-dom";
import { Redirect } from 'react-router-dom';
import SirioBarChart from "../../Components/Chart/SirioBarChart";
import SirioDashboardBox from "../../Components/Box/SirioDashboardBox";
import DashboardService from "../../Services/DashboardService";
import KantorCabangService from "../../Services/KantorCabangService";
import SirioField from "../../Components/Form/SirioFormComponent/SirioField";
import SirioButton from '../../Components/Button/SirioButton';
import SirioForm from "../../Components/Form/SirioForm";
import SirioComponentHeader from "../../Components/Header/SirioComponentHeader";
import AuthenticationService from "../../Services/AuthenticationService";

class DashboardKantorCabang extends React.Component {

    constructor(props) {
        super(props);

        // state untuk menjalankan preloader saat halaman diakses
        this.state = {
            preloader: true,
            contentLoading: !PollingService.isConnected(),
            dashboardComponent: {},
            kantorCabangList: [],
            namaKantorList: [],
            areaKantorList: [],
            regionalKantorList: [],
            namaKantor: "",
            areaKantor: "",
            regionalKantor: "",
            tanggalAwal: "",
            tanggalAkhir: "",
            namaChanged: false,
            role: AuthenticationService.getRole(),
            redirect: false
        }
        this.renderData = this.renderData.bind(this);
        // this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleSelectChangeNama = this.handleSelectChangeNama.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    // jika sudah selesai dirender, hentikan preloader
    componentDidMount() {
        this.renderData();
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

    async renderData() {
        const responseDashboardComp = await DashboardService.getAllComponent();
        const dashboardComponent = responseDashboardComp.data.result;

        const responseKantorCabang = await KantorCabangService.getKantorCabangList();
        const kantorCabangList = responseKantorCabang.data.result;

        const namaKantorCabang = responseKantorCabang.data.result.map(kc => {
            return (
                {
                    label: kc.namaKantor,
                    value: kc.namaKantor
                }
            )
        });
        const areaKantorCabang = responseKantorCabang.data.result.map(kc => {
            return (
                {
                    label: kc.area,
                    value: kc.area
                }
            )
        });
        const regionalKantorCabang = responseKantorCabang.data.result.map(kc => {
            return (
                {
                    label: kc.regional,
                    value: kc.regional
                }
            )
        });

        this.setState({
            dashboardComponent: dashboardComponent,
            kantorCabangList: kantorCabangList,
            namaKantorList: namaKantorCabang,
            areaKantorList: areaKantorCabang,
            regionalKantorList: regionalKantorCabang
        })
    }

    getUnique(arr, index) {
        const unique = arr
            .map(e => e[index])
            .map((e, i, final) => final.indexOf(e) === i && i)
            .filter(e => arr[e]).map(e => arr[e]); 
        return unique;
    }

    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    handleSelectChangeNama(name, event) {
        const listKC = this.state.kantorCabangList;
        for (var i in listKC) {
            if (event.value === listKC[i].namaKantor) {
                this.setState(
                    {
                        [name]
                            : event.value,
                        areaKantorList: listKC[i].area,
                        regionalKantorList: listKC[i].regional,
                        namaChanged: true
                    }
                )
            }
        }
    }

    handleChange(event) {
        const { name, value } = event.target;
        this.setState(
            {
                [name]
                    : value
            }
        )
    }

    handleSubmit(event) {
        event.preventDefault();
        const filter = {
            namaKantor: this.state.namaKantor,
            areaKantor: this.state.areaKantor,
            regionalKantor: this.state.regionalKantor
        };
        DashboardService.getAllComponentByFilter(filter)
            .then((response) => 
            this.setState({
                dashboardComponent: response.data.result,
                namaKantor: "",
                areaKantor: "",
                regionalKantor: ""
            }));
    }

    getBetween() {
        return (
            <>
                <div className="col-md-1 pl-0">
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChangeNama}
                        classes="p-1"
                        name="namaKantor"
                        value={this.state.namaKantor}
                        optionList={this.state.namaKantorList}
                    />
                </div>
                <div className="col-md-1 pl-0">
                    {this.state.namaChanged ? <h5>{this.state.areaKantorList}</h5> :
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChange}
                        classes="p-1"
                        name="areaKantor"
                        value={this.state.areaKantor}
                        optionList={this.getUnique(this.state.areaKantorList, 'label')}
                    />
                    }
                </div>
                <div className="col-md-1 pl-0">
                    {this.state.namaChanged ? <h5>{this.state.regionalKantorList}</h5> :
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChange}
                        classes="p-1"
                        name="regionalKantor"
                        value={this.state.regionalKantor}
                        optionList={this.getUnique(this.state.regionalKantorList, 'label')}
                    />
                    }
                </div>
                <div className="col-md-6 pl-0">
                    <SirioField
                        type="date"
                        handleChange={this.handleChange}
                        classes="p-1"
                        name="tanggalAwal"
                        value={this.state.tanggalAwal}
                    />
                </div>
                <div className="col-md-6 pl-0">
                    <SirioField
                        type="date"
                        handleChange={this.handleChange}
                        classes="p-1"
                        name="tanggalAkhir"
                        value={this.state.tanggalAkhir}
                    />
                </div>
            </>
        )
    }

    getChart() {
        return({
            labels: this.state.dashboardComponent.daftarBulan,
            datasets: [
                {
                    label: 'Jumlah Temuan',
                    stack: 'Stack 0',
                    borderWidth: 1,
                    backgroundColor: '#7F3F98',
                    borderColor: '#7F3F98',
                    hoverBackgroundColor: '#a665bf',
                    hoverBorderColor: '#7F3F98',
                    data: this.state.dashboardComponent.jumlahTemuanPerBulan
                },
                {
                    label: 'Jumlah Rekomendasi Diimplementasi',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    backgroundColor: '#5DBCD2',
                    borderColor: '#5DBCD2',
                    hoverBackgroundColor: '#99d5e3',
                    hoverBorderColor: '#5DBCD2',
                    data: this.state.dashboardComponent.jumlahRekomendasiImplementedPerBulan
                },
                {
                    label: 'Jumlah Rekomendasi Belum Diimplementasi',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    backgroundColor: '#6FCF97',
                    borderColor: '#6FCF97',
                    hoverBackgroundColor: '#a8e2c0',
                    hoverBorderColor: '#6FCF97',
                    data: this.state.dashboardComponent.jumlahRekomendasiNotImplementedPerBulan
                },
                {
                    label: 'Jumlah Rekomendasi Overdue',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    backgroundColor: '#F2C94C',
                    borderColor: '#F2C94C',
                    hoverBackgroundColor: '#f7df93',
                    hoverBorderColor: '#F2C94C',
                    data: this.state.dashboardComponent.jumlahRekomendasiOverduePerBulan
                }
            ]
        })
    }

    getBoxFirst() {
        return([
            {
                title: "Rekomendasi Diimplementasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiImplemented}%</p>
            },
            {
                title: "Rekomendasi Belum Diimplementasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiNotImplemented}%</p>
            },
            {
                title: "Rekomendasi Overdue",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiOverdue}%</p>
            },
            {
                title: "Jumlah Temuan",
                value: <p>{this.state.dashboardComponent.jumlahTemuan}</p>
            },
            {
                title: "Jumlah Rekomendasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasi}</p>
            }
        ])
    }

    getBoxSecond() {
        return([
            {
                title: "Rekomendasi Diimplementasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiImplemented}%</p>
            },
            {
                title: "Rekomendasi Belum Diimplementasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiNotImplemented}%</p>
            },
            {
                title: "Rekomendasi Overdue",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiOverdue}%</p>
            },
            {
                title: "Jumlah Temuan",
                value: <p>{this.state.dashboardComponent.jumlahTemuan}</p>
            },
            {
                title: "Jumlah Rekomendasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasi}</p>
            },
            {
                title: "Jumlah Pemeriksaan",
                value: <p>{this.state.dashboardComponent.jumlahPemeriksaan}</p>
            },
            {
                title: "Risk Score",
                value: <p>{this.state.dashboardComponent.riskScore}</p>
            }
        ])
    }

    render() {
        const { preloader, contentLoading, loadingBody } = this.state;
        const role = this.state.role;
        const qaOfficer = role === "QA Officer Operational Risk" || role === "Super QA Officer Operational Risk";
        const branchManager = role === "Branch Manager";

        if (qaOfficer) {
            return (
                <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                    <div>
                        <h3>Dashboard Performa Seluruh Kantor Cabang</h3>
                        <SirioComponentHeader
                            betweenTitleSubtitle={this.getBetween()}
                        />
                    </div>
                    <div>
                        <SirioButton
                            purple
                            recommended
                            classes="mx-2"
                            onClick={(event) => this.handleSubmit(event)}
                        >
                            Cari
                        </SirioButton>
                    </div>
                    <div>
                        <br></br>
                        <SirioBarChart data={this.getChart()} />
                    </div>
                    <div>
                        
                            <SirioDashboardBox data={this.getBoxFirst()} />
                        
                    </div>
                </SirioMainLayout>
            )
        } else if (branchManager) {
            return (
                <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                    <div>
                        <h3>Dashboard Performa Kantor Cabang</h3>
                    </div>
                    <div>
                        <br></br>
                        <SirioBarChart data={this.getChart()} />
                    </div>
                    <div>
                        <SirioDashboardBox data={this.getBoxSecond()} />
                    </div>
                </SirioMainLayout>
            )
        }
    }
}

export default withRouter(DashboardKantorCabang);